package com.shamardin.advancededitor.controller;

import com.shamardin.advancededitor.core.git.VcsProcessor;
import com.shamardin.advancededitor.view.CreateRepositoryDialog;
import com.shamardin.advancededitor.view.GitFilesListPanel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

import static java.awt.Color.PINK;

@Slf4j
@Component
public class GitController {
    @Autowired
    private VcsProcessor vcsProcessor;

    @Autowired
    private OpenFileController openFileController;

    @Autowired
    private GitFilesListPanel gitFilesListPanel;
    private boolean isOpened;

    public void openRepository(File file) {
        isOpened = vcsProcessor.openRepository(file);
        if(!isOpened) {
            if(CreateRepositoryDialog.isNeedToCreateRepository()) {
                vcsProcessor.createRepository(file);
                //refresh
                openFileController.showFileTree(file);
            }
        }
        refreshGitPanel();
    }

    public void addFileToVcs(File file) {
        vcsProcessor.addFile(file);
    }

    public void refreshGitPanel() {
        gitFilesListPanel.clearGitFileList();
        for (String s : vcsProcessor.getUntrackedFiles()) {
            gitFilesListPanel.addFileInList(new File(s), PINK);
        }
    }
}
