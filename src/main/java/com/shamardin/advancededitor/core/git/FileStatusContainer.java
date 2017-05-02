package com.shamardin.advancededitor.core.git;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.shamardin.advancededitor.core.git.FileStatus.MODIFIED;

@Slf4j
@Component
public class FileStatusContainer {
    @Autowired
    private VcsProcessor vcsProcessor;

    /**
     * Contains relative path and status
     */
    private Map<File, FileStatus> fileStatusMap = new ConcurrentHashMap<>();

    public Color getFileColor(File file) {
        return fileStatusMap.computeIfAbsent(file, vcsProcessor::computeFileStatus).getColor();
    }

    public void updateFileStatus(File file) {
        fileStatusMap.put(file, vcsProcessor.computeFileStatus(file));
    }

    public void setFileAsModified(File file) {
        fileStatusMap.put(file, MODIFIED);
    }

    public FileStatus getFileStatus(File file) {
        return fileStatusMap.get(file);
    }

}
