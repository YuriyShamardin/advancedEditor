package com.shamardin.advancededitor.core.fileloading;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import static com.google.common.collect.Lists.newArrayList;

@Slf4j
@Component
class TrackedFileContainer {
    /**
     * Contains full path and content
     */
    private Map<File, String> filesContent = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        FileSynchronizer fileSynchronizer = new FileSynchronizer();
        Timer timer = new Timer(5_000, fileSynchronizer);
//        timer.start();
    }

    String get(File file) {
        return filesContent.get(file);
    }

    boolean containsFile(File file) {
        return filesContent.containsKey(file);
    }

    List<File> getAllFiles() {
        return newArrayList(filesContent.keySet());
    }

    String computeIfAbsent(File file, Function<File, String> mappingFnction) {
        return filesContent.computeIfAbsent(file, mappingFnction);
    }

    private class FileSynchronizer implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    for (Map.Entry<File, String> fileEntry : filesContent.entrySet()) {
                        log.info("write to {}", fileEntry.getKey());
                        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileEntry.getKey()))) {
                            bufferedWriter.write(fileEntry.getValue());
                        }
                    }
                    return null;
                }
            }.execute();
        }
    }

    void addFile(File file, String content) {
        filesContent.put(file, content);
    }

    void remove(File file) {
        if(filesContent.remove(file) == null) {
            log.info("can not find file {}", file);
        }
    }
}
