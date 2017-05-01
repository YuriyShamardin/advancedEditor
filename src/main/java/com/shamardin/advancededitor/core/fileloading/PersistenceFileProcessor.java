package com.shamardin.advancededitor.core.fileloading;

import com.google.common.annotations.VisibleForTesting;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static java.lang.Integer.MAX_VALUE;
import static lombok.AccessLevel.PACKAGE;

@Slf4j
@Component
public class PersistenceFileProcessor implements FileProcessor {

    @VisibleForTesting
    @Setter(PACKAGE)
    @Autowired
    private FileContainer fileContainer;

    @Override
    public synchronized String loadFileInContainer(File file) {
        String content = readFileFromDisk(file);
        fileContainer.addFile(file, content);
        return fileContainer.get(file);
    }

    private String readFileFromDisk(File file) {
        final long dataLength = file.length();
        int bufLength = dataLength > MAX_VALUE ? MAX_VALUE : (int) dataLength;
        byte[] buffer = new byte[bufLength];

        StringBuilder stringBuilder = new StringBuilder();

        try (final FileInputStream fis = (new FileInputStream(file))) {
            int readData;
            while ((readData = fis.read(buffer)) > 0) {
                log.info("read {} bytes from file {}. Full size is {}", readData, file.getName(), dataLength);
                stringBuilder.append(new String(buffer));
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return "Can not open the file! For details look at log file";
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean isLoaded(File file) {
        return fileContainer.containsFile(file);
    }

    @Override
    public void removeFile(File file) {
        fileContainer.remove(file);
    }

    @Override
    public List<File> getAllTrackedFiles() {
        return fileContainer.getAllFiles();
    }

   /* @Override
    public String readFile(File file) {
        new SwingWorker<Void, Void>() {
            private String content;

            @Override
            protected Void doInBackground() throws Exception {
                content = readFileFromDisk(file);
                return null;
            }

            @Override
            protected void done() {
                fileContainer.addFile(file, content);
            }
        }.execute();
        return null;
    }*/
}
