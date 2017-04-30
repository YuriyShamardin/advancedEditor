package com.shamardin.advancededitor.view;

import com.shamardin.advancededitor.controller.GitController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;

@Slf4j
@Component
public class ButtonsPanel extends JPanel {
    @Autowired
    private GitController gitController;

    @Autowired
    private GitFilesListPanel gitFilesListPanel;

    @PostConstruct
    public void init() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        add(new JLabel("Buttons"));
        JButton addToGit = new JButton("AddToGit");
        addToGit.addActionListener(e -> {
            gitController.addFileToVcs(gitFilesListPanel.getSelectedFile());
            gitController.refreshGitPanel();
        });
        add(addToGit);

        JButton refreshGitTree = new JButton("Refresh");
        refreshGitTree.addActionListener(e -> gitController.refreshGitPanel());
        add(refreshGitTree);
    }

}
