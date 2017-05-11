package com.shamardin.advancededitor.controller;

import com.shamardin.advancededitor.core.fileloading.FileProcessor;
import com.shamardin.advancededitor.core.git.FileStatusContainer;
import com.shamardin.advancededitor.core.git.VcsProcessor;
import com.shamardin.advancededitor.view.CreateRepositoryDialog;
import com.shamardin.advancededitor.view.GitFilesListPanel;
import com.shamardin.advancededitor.view.UntrackFilePanel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.io.File;
import java.util.List;

import static com.shamardin.advancededitor.core.PathUtil.getFileWithRelativePath;
import static com.shamardin.advancededitor.core.PathUtil.getRoot;

@Slf4j
@Component
public class GitController implements VcsController {
    @Autowired
    private FileTreeController fileTreeController;

    @Autowired
    private FileStatusContainer fileStatusContainer;

    @Autowired
    private VcsProcessor vcsProcessor;

    @Autowired
    private FileProcessor fileProcessor;

    @Autowired
    private GitFilesListPanel gitFilesListPanel;

    @Autowired
    private UntrackFilePanel untrackFilePanel;

    private static int SHOW_AS_TRACKED = 1;
    private static int SHOW_AS_UNTRACKED = 0;

    @Override
    public void openRepository(File file) {
        boolean isOpened = vcsProcessor.openRepository(file);
        if(!isOpened && CreateRepositoryDialog.isNeedToCreateRepository()) {
            vcsProcessor.createRepository(file);
            //refresh
            fileTreeController.showFileTree(file);
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
        new SwingWorker<Void, Pair<Integer, File>>() {
            @Override
            protected Void doInBackground() throws Exception {
                List<File> fileList = fileProcessor.getAllTrackedFiles();
                gitFilesListPanel.clearGitFileList();
                untrackFilePanel.clearUntrackFileList();
                for(File file : fileList) {
                    fileStatusContainer.updateFileStatus(getFileWithRelativePath(file));
                    publish(Pair.of(SHOW_AS_TRACKED, file));
                }
                List<String> removedFiles = vcsProcessor.getAllRemovedFiles();
                for(String string : removedFiles) {
                    publish(Pair.of(SHOW_AS_TRACKED, new File(string)));
                }
                List<String> allUntrackedFiles = vcsProcessor.getAllUntrackedFiles();
                for(String untrackedFile : allUntrackedFiles) {
                    publish(Pair.of(SHOW_AS_UNTRACKED, new File(untrackedFile)));
                }
                return null;
            }

            @Override
            protected void process(List<Pair<Integer, File>> files) {
                for(Pair<Integer, File> pair : files) {
                    if(pair.getKey() == SHOW_AS_TRACKED) {
                        gitFilesListPanel.addFileInList(getFileWithRelativePath(pair.getRight()));
                    } else {
                        untrackFilePanel.addFileInList(getFileWithRelativePath(pair.getRight()));
                    }
                }
            }

            @Override
            protected void done() {
                gitFilesListPanel.repaint();
                untrackFilePanel.repaint();
            }
        }.execute();
    }

    @Override
    public void refreshGitPanel() {
        new SwingWorker<Void, Pair<Integer, File>>() {
            @Override
            protected Void doInBackground() throws Exception {
                List<File> fileList = fileProcessor.getAllTrackedFiles();
                gitFilesListPanel.clearGitFileList();
                untrackFilePanel.clearUntrackFileList();
                for(File file : fileList) {
                    fileStatusContainer.getFileStatus(getFileWithRelativePath(file));
                    publish(Pair.of(SHOW_AS_TRACKED, file));
                }

                List<String> removedFiles = vcsProcessor.getAllRemovedFiles();
                for(String string : removedFiles) {
                    publish(Pair.of(SHOW_AS_TRACKED, new File(string)));
                }
                List<String> allUntrackedFiles = vcsProcessor.getAllUntrackedFiles();
                for(String untrackedFile : allUntrackedFiles) {
                    publish(Pair.of(SHOW_AS_UNTRACKED, new File(untrackedFile)));
                }
                return null;
            }

            @Override
            protected void process(List<Pair<Integer, File>> files) {
                for(Pair<Integer, File> pair : files) {
                    if(pair.getKey() == SHOW_AS_TRACKED) {
                        gitFilesListPanel.addFileInList(getFileWithRelativePath(pair.getRight()));
                    } else {
                        untrackFilePanel.addFileInList(getFileWithRelativePath(pair.getRight()));
                    }
                }
            }

            @Override
            protected void done() {
                gitFilesListPanel.repaint();
                untrackFilePanel.repaint();
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
