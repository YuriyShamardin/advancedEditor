package com.shamardin.advancededitor.controller;

import com.shamardin.advancededitor.core.fileloading.FileProcessor;
import com.shamardin.advancededitor.core.git.FileStatusContainer;
import com.shamardin.advancededitor.core.git.VcsProcessor;
import com.shamardin.advancededitor.view.CreateRepositoryDialog;
import com.shamardin.advancededitor.view.FileTreePanel;
import com.shamardin.advancededitor.view.GitFilesListPanel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.io.File;
import java.util.List;

import static com.shamardin.advancededitor.PathUtil.getFileWithRelativePath;
import static com.shamardin.advancededitor.PathUtil.getRoot;

@Slf4j
@Component
public class GitController implements VcsController {
    @Autowired
    private VcsProcessor vcsProcessor;

    @Autowired
    private FileTreeController fileTreeController;

    @Autowired
    private GitFilesListPanel gitFilesListPanel;

    @Autowired
    private FileStatusContainer fileStatusContainer;

    @Autowired
    private FileTreePanel fileTreePanel;

    @Autowired
    private FileProcessor fileProcessor;

    @Override
    public void openRepository(File file) {
        boolean isOpened = vcsProcessor.openRepository(file);
        if(!isOpened) {
            if(CreateRepositoryDialog.isNeedToCreateRepository()) {
                vcsProcessor.createRepository(file);
                //refresh
                fileTreeController.showFileTree(file);
            }
        }
        updateGitPanel();
    }

    @Override
    public void addFileToVcs(File file) {
        vcsProcessor.addFile(file);
        updateGitPanel();
    }

    @Override
    public void updateGitPanel() {
        new SwingWorker<Void, File>() {
            @Override
            protected Void doInBackground() throws Exception {
                List<File> fileList = fileProcessor.getAllTrackedFiles();
                gitFilesListPanel.clearGitFileList();
                gitFilesListPanel.repaint();
                for (File file : fileList) {
                    fileStatusContainer.updateFileStatus(getFileWithRelativePath(file));
                    publish(file);
                }
                List<String> removedFiles = vcsProcessor.getAllRemovedFiles();
                for (String string : removedFiles) {
                    publish(new File(string));
                }
                return null;
            }

            @Override
            protected void process(List<File> files) {
                for (File file : files) {
                    gitFilesListPanel.addFileInList(getFileWithRelativePath(file));
                }
            }

            @Override
            protected void done() {
                gitFilesListPanel.repaint();
            }
        }.execute();
    }

    @Override
    public void refreshGitPanel() {
        new SwingWorker<Void, File>() {
            @Override
            protected Void doInBackground() throws Exception {
                List<File> fileList = fileProcessor.getAllTrackedFiles();
                gitFilesListPanel.clearGitFileList();
                gitFilesListPanel.repaint();
                for (File file : fileList) {
                    fileStatusContainer.getFileStatus(getFileWithRelativePath(file));
                    publish(file);
                }

                List<String> removedFiles = vcsProcessor.getAllRemovedFiles();
                for (String string : removedFiles) {
                    publish(new File(string));
                }
                return null;
            }

            @Override
            protected void process(List<File> files) {
                for (File file : files) {
                    gitFilesListPanel.addFileInList(getFileWithRelativePath(file));
                }
            }

            @Override
            protected void done() {
                gitFilesListPanel.repaint();
            }
        }.execute();
    }

    @Override
    public void removeFromVcs(File file) {
        vcsProcessor.removeFile(file);
        fileTreeController.showFileTree(new File(getRoot()));
        updateGitPanel();
    }

    @Override
    public void revert(File file) {
        vcsProcessor.revertFile(file);
        fileTreeController.showFileTree(new File(getRoot()));
        updateGitPanel();
    }
}
