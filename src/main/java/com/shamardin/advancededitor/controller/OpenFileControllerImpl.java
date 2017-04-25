package com.shamardin.advancededitor.controller;

import com.shamardin.advancededitor.view.FilesListPanel;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class OpenFileControllerImpl implements OpenFileController {

    @Autowired
    private FilesListPanel filesListPanel;

    @Autowired
    private ChooseFileControllerImpl chooseFileController;


    @Override
    @SneakyThrows
    public void showFileInTextArea(File file) {
        filesListPanel.addFileInList(file);
        chooseFileController.chooseFile(file);

    }
}
