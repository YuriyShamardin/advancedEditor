package com.shamardin.advancededitor.listener;

import com.shamardin.advancededitor.controller.VcsController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Slf4j
@Component
public class RefreshVcsTreeButtonListener implements ActionListener {
    @Autowired
    private VcsController vcsController;

    @Override
    public void actionPerformed(ActionEvent e) {
        vcsController.updateGitPanel();
    }
}
