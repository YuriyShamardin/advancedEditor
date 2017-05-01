package com.shamardin.advancededitor.controller;

import com.shamardin.advancededitor.core.git.FileStatusContainer;
import com.shamardin.advancededitor.view.FileContentArea;
import com.shamardin.advancededitor.view.FileTreePanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

@Component
public class FileChangeController implements KeyListener {
    @Autowired
    private FileTreePanel fileTreePanel;
    @Autowired
    private FileStatusContainer fileStatusContainer;
    @Autowired
    private FileTreeController fileTreeController;

    @Override
    public void keyTyped(KeyEvent e) {
        SwingWorker<Void, Void> statusUpdater = new SwingWorker<Void, Void>() {
            private File file;

            @Override
            protected Void doInBackground() throws Exception {
                FileContentArea fileContentArea = (FileContentArea) e.getSource();
                String rootPath = fileTreeController.getRootPath();
                String fullPath = fileTreePanel.getSelectedFilePath();
                file = new File(fullPath.replace(rootPath, ""));
                fileStatusContainer.updateFileStatus(file);
                return null;
            }


            @Override
            protected void done() {
                fileStatusContainer.updateFileStatus(file);
            }
        };
        statusUpdater.execute();
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
