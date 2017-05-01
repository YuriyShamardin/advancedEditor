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

import static com.google.common.collect.Lists.newArrayList;

@Component
@Slf4j
class FileContainer {
    //    private LoadingCache<File, String> filesContent;
    private Map<File, String> filesContent = new ConcurrentHashMap<>();

//    @Autowired
//    private FileProcessor fileProcessor;

    @PostConstruct
    public void init() {
//        filesContent = CacheBuilder.newBuilder()
//                in bytes
//                .maximumWeight(1000000)
//                .weigher((Weigher<File, String>) (k, content) -> content.getBytes().length)
//                .build(new CacheLoader<File, String>() {
//                    @Override
//                    public String load(File key) throws Exception {
//                        return fileProcessor.readFileFromDisk(key);
//                    }
//                });

        FileSynchronizer fileSynchronizer = new FileSynchronizer();
        Timer timer = new Timer(5_0000, fileSynchronizer);
//        timer.start();
    }

    String get(File file) {
        return filesContent.get(file);
    }

    boolean containsFile(File file) {
        return filesContent.containsKey(file);
    }

    public List<File> getAllFiles() {
        return newArrayList(filesContent.keySet());
    }

    private class FileSynchronizer implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    for (Map.Entry<File, String> fileEntry : filesContent.entrySet()) {
                        log.info("write to {}", fileEntry.getKey());
                        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileEntry.getKey()));
                        bufferedWriter.write(fileEntry.getValue());
                    }
                    return null;
                }
            }.execute();
        }
    }

    void addFile(File file, String content) {
        filesContent.put(file, content);
    }

    public void remove(File file) {
        if(filesContent.remove(file) == null) {
            log.info("can not find file {}", file);
        }
    }
}
