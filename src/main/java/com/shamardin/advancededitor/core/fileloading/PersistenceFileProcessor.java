package com.shamardin.advancededitor.core.fileloading;

import com.google.common.annotations.VisibleForTesting;
import com.shamardin.advancededitor.controller.VcsController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.io.*;
import java.util.List;

import static java.lang.Integer.MAX_VALUE;
import static org.apache.commons.lang3.exception.ExceptionUtils.getMessage;

@Slf4j
@Component
class PersistenceFileProcessor implements FileProcessor {

    @Autowired
    private TrackedFileContainer trackedFileContainer;

    // TODO: 10-May-17 refactor it, probably using event-driven development(?)
    @Autowired
    private VcsController vcsController;

    @Override
    public synchronized String getFileContent(File file) {
        return trackedFileContainer.computeIfAbsent(file, this::readFileFromDisk);
    }

    @VisibleForTesting
    String readFileFromDisk(File file) {
        if(file == null) {
            return "Can not open the file! For details look at log file";
        }
        final long dataLength = file.length();
        int bufLength = dataLength > MAX_VALUE ? MAX_VALUE : (int) dataLength;
        byte[] buffer = new byte[bufLength];

        StringBuilder stringBuilder = new StringBuilder();

        try(FileInputStream fis = new FileInputStream(file)) {
            int readData;
            while((readData = fis.read(buffer)) > 0) {
                log.info("read {} bytes from file {}. Full size is {}", readData, file.getName(), dataLength);
                stringBuilder.append(new String(buffer));
            }
        } catch(IOException e) {
            log.error(getMessage(e));
            return "Can not open the file! For details look at log file";
        }
        return stringBuilder.toString();
    }

    @Override
    public void unTrackFile(File file) {
        trackedFileContainer.remove(file);
    }

    @Override
    public List<File> getAllTrackedFiles() {
        return trackedFileContainer.getAllFiles();
    }

    @Override
    public void saveFile(File file, String content) {
        String oldValue = trackedFileContainer.addFile(file, content);
        if(oldValue != null && !oldValue.equals(content)) {
            saveFileOnDisk(file, content);
        }
    }

    private void saveFileOnDisk(File file, String content) {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                log.info("starting to save {} ...", file);
                try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
                    bufferedWriter.write(content);
                }
                return null;
            }

            @Override
            protected void done() {
                log.info("file {} successfully saved on disk", file);
                vcsController.updateGitPanel();
            }
        }.execute();
    }
}
