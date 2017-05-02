package com.shamardin.advancededitor.controller;

import com.shamardin.advancededitor.PathUtil;
import com.shamardin.advancededitor.core.fileloading.FileProcessor;
import com.shamardin.advancededitor.core.git.FileStatusContainer;
import com.shamardin.advancededitor.view.FileContentArea;
import com.shamardin.advancededitor.view.FileTreePanel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.List;

import static com.shamardin.advancededitor.core.git.FileStatus.MODIFIED;

@Slf4j
@Component
public class FileChangeListener implements KeyListener {
    @Autowired
    private FileTreePanel fileTreePanel;
    @Autowired
    private FileStatusContainer fileStatusContainer;
    @Autowired
    private FileTreeController fileTreeController;

    @Autowired
    private VCSController vcsController;
    @Autowired
    private FileProcessor fileProcessor;

    @Override
    public void keyTyped(KeyEvent e) {
        SwingWorker<Void, Void> statusUpdater = new SwingWorker<Void, Void>() {
            private File file;

            @Override
            protected Void doInBackground() throws Exception {
                FileContentArea fileContentArea = (FileContentArea) e.getSource();
                String fullPath = fileTreePanel.getSelectedFilePath();

                String persistContent = fileProcessor.getFileContent(new File(fullPath));
                String newContent = fileContentArea.getText();

                file = PathUtil.getFileWithRelativePath(fullPath);
                if(!persistContent.equals(newContent)) {
                    if(fileStatusContainer.getFileStatus(file) != MODIFIED) {
                        fileStatusContainer.setFileAsModified(file);
                        publish();
                    }
                } else {
                    fileStatusContainer.updateFileStatus(file);
                    publish();
                }
                return null;
            }

            @Override
            protected void process(List<Void> chunks) {
                vcsController.refreshGitPanel();
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
