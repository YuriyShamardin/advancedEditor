package com.shamardin.advancededitor.controller;

import com.shamardin.advancededitor.Editor;
import com.shamardin.advancededitor.core.fileloading.FileProcessor;
import com.shamardin.advancededitor.view.FileContentArea;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.io.File;

@Slf4j
@Component
public class ChooseFileControllerImpl implements ChooseFileController {
    @Autowired
    private FileProcessor fileProcessor;
    @Autowired
    private FileContentArea fileContentArea;
    @Autowired
    private Editor editor;

    @Override
    public void chooseFile(File file) {
        byte[] content = fileProcessor.load(file);

        fileContentArea.setText("");
        fileContentArea.append(new String(content));

        editor.repaint();
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        File file = (File) ((JList) e.getSource()).getSelectedValue();
        log.info("choose file {}",file);
        chooseFile(file);
    }
}
