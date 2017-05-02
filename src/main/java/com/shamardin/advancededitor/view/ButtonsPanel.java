package com.shamardin.advancededitor.view;

import com.shamardin.advancededitor.controller.GitController;
import com.shamardin.advancededitor.core.git.FileStatusContainer;
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
    private FileStatusContainer fileStatusContainer;

    @Autowired
    private GitFilesListPanel gitFilesListPanel;

    @PostConstruct
    public void init() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        add(new JLabel("Buttons"));

        JButton addToGit = new JButton("Add to git");
        addToGit.addActionListener(e -> {
            gitController.addFileToVcs(gitFilesListPanel.getSelectedFile());
        });
        add(addToGit);

        JButton removeFromGit = new JButton("Remove");
        removeFromGit.addActionListener(e -> {
            gitController.removeFromVcs(gitFilesListPanel.getSelectedFile());
        });
        add(removeFromGit);

        JButton revert = new JButton("Revert");
        revert.addActionListener(e -> {
            gitController.revert(gitFilesListPanel.getSelectedFile());
        });
        add(revert);

        JButton refreshGitTree = new JButton("Refresh");
        refreshGitTree.addActionListener(e -> gitController.updateGitPanel());
        add(refreshGitTree);
    }
}