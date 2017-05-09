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

    String addFile(File file, String content) {
        return filesContent.put(file, content);
    }

    void remove(File file) {
        if(filesContent.remove(file) == null) {
            log.info("can not find file {}", file);
        }
    }
}
