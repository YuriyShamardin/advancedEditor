package com.shamardin.advancededitor.listener;

import com.shamardin.advancededitor.controller.VcsController;
import com.shamardin.advancededitor.view.GitFilesListPanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
public class RemoveFromVcsButtonListener implements ActionListener {
    @Autowired
    private VcsController vcsController;
    @Autowired
    private GitFilesListPanel gitFilesListPanel;

    @Override
    public void actionPerformed(ActionEvent e) {
        vcsController.removeFromVcs(gitFilesListPanel.getSelectedFile());
    }
}
