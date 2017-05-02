package com.shamardin.advancededitor.listener;

import com.shamardin.advancededitor.controller.VcsController;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RefreshVcsTreeButtonListener implements ActionListener {
    @Autowired
    private VcsController vcsController;

    @Override
    public void actionPerformed(ActionEvent e) {
        vcsController.updateGitPanel();
    }
}
