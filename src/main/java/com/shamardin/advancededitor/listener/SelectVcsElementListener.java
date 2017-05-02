package com.shamardin.advancededitor.listener;

import com.shamardin.advancededitor.controller.VcsController;
import com.shamardin.advancededitor.core.git.FileStatus;
import com.shamardin.advancededitor.core.git.FileStatusContainer;
import com.shamardin.advancededitor.view.ButtonsPanel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.io.File;

@Slf4j
@Component
public class SelectVcsElementListener implements ListSelectionListener {
    @Autowired
    private VcsController vcsController;
    @Autowired
    private FileStatusContainer fileStatusContainer;
    @Autowired
    private ButtonsPanel buttonsPanel;

    @Override
    @SuppressWarnings("unchecked")
    public void valueChanged(ListSelectionEvent e) {
        if(!e.getValueIsAdjusting()) {
            File selectedFile = ((JList<File>) e.getSource()).getSelectedValue();
            if(selectedFile == null) {
                return;
            }
            FileStatus fileStatus = fileStatusContainer.getFileStatus(selectedFile);
            switch (fileStatus) {
                case UNTRACKED:
                    buttonsPanel.getAddToGit().setEnabled(true);
                    buttonsPanel.getRemoveFromGit().setEnabled(false);
                    buttonsPanel.getRevert().setEnabled(false);
                    break;
                case ADDED:
                    buttonsPanel.getAddToGit().setEnabled(false);
                    buttonsPanel.getRemoveFromGit().setEnabled(false);
                    buttonsPanel.getRevert().setEnabled(false);
                    break;
                case REMOVED:
                    buttonsPanel.getAddToGit().setEnabled(false);
                    buttonsPanel.getRemoveFromGit().setEnabled(false);
                    buttonsPanel.getRevert().setEnabled(true);
                    break;
                case MODIFIED:
                    buttonsPanel.getAddToGit().setEnabled(true);
                    buttonsPanel.getRevert().setEnabled(true);
                    buttonsPanel.getRemoveFromGit().setEnabled(false);
                    break;
                case VERSIONED:
                    buttonsPanel.getAddToGit().setEnabled(false);
                    buttonsPanel.getRevert().setEnabled(false);
                    buttonsPanel.getRemoveFromGit().setEnabled(true);
                    break;
                case UNKNOWN:
                    buttonsPanel.getAddToGit().setEnabled(true);
                    buttonsPanel.getRevert().setEnabled(true);
                    buttonsPanel.getRemoveFromGit().setEnabled(true);
                    break;
                default:
                    buttonsPanel.getAddToGit().setEnabled(false);
                    buttonsPanel.getRevert().setEnabled(false);
                    buttonsPanel.getRemoveFromGit().setEnabled(false);
            }
        }
    }
}

