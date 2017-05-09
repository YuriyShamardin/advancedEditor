package com.shamardin.advancededitor.listener;

import com.shamardin.advancededitor.controller.VcsController;
import com.shamardin.advancededitor.core.fileloading.FileProcessor;
import com.shamardin.advancededitor.view.FileContentArea;
import com.shamardin.advancededitor.view.FileContentTab;
import com.shamardin.advancededitor.view.FileTreePanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

@Component

public class SaveFileOnDiskListener implements ActionListener {
    @Autowired
    private FileTreePanel fileTreePanel;

    @Autowired
    private FileContentTab fileContentTab;

    @Autowired
    private FileProcessor fileProcessor;

    @Override
    public synchronized void actionPerformed(ActionEvent e) {
        String selectedFilePath = fileTreePanel.getSelectedFilePath();
        FileContentArea fileContentArea = (FileContentArea) ((JScrollPane) fileContentTab.getSelectedComponent()).getViewport().getView();
        String newContent = fileContentArea.getText();
        fileProcessor.saveFile(new File(selectedFilePath), newContent);
    }
}
