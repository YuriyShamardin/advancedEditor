package com.shamardin.advancededitor.controller;

import com.shamardin.advancededitor.core.fileloading.FileProcessor;
import com.shamardin.advancededitor.view.FilesListPanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class OpenFileControllerImpl implements OpenFileController {

    @Autowired
    private FilesListPanel filesListPanel;

    @Autowired
    private FileProcessor fileProcessor;

    @Override
    public void showFileInTextArea(File file) {
        if(fileProcessor.isLoaded(file)) {
            filesListPanel.chooseFileInList(file);
        } else {
            filesListPanel.addFileInList(file);
        }
    }
}
