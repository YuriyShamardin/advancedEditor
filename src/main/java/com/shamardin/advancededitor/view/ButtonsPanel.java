package com.shamardin.advancededitor.view;

import com.shamardin.advancededitor.listener.AddFileToVcsButtonListener;
import com.shamardin.advancededitor.listener.RefreshVcsTreeButtonListener;
import com.shamardin.advancededitor.listener.RemoveFromVcsButtonListener;
import com.shamardin.advancededitor.listener.RevertFileInVcsButtonListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;

@Slf4j
@Component
public class ButtonsPanel extends JPanel {
    @Autowired
    private GitFilesListPanel gitFilesListPanel;
    @Autowired
    private AddFileToVcsButtonListener addFileToVcsButtonListener;
    @Autowired
    private RemoveFromVcsButtonListener removeFromVcsButtonListener;
    @Autowired
    private RevertFileInVcsButtonListener revertFileInVcsButtonListener;
    @Autowired
    private RefreshVcsTreeButtonListener refreshVcsTreeButtonListener;

    private JButton refreshGitTree = new JButton("Refresh");
    private JButton revert = new JButton("Revert");
    private JButton removeFromGit = new JButton("Remove");
    private JButton addToGit = new JButton("Add to git");

    @PostConstruct
    public void init() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        add(new JLabel("Buttons"));

        addToGit.addActionListener(addFileToVcsButtonListener);
        add(addToGit);

        removeFromGit.addActionListener(removeFromVcsButtonListener);
        add(removeFromGit);

        revert.addActionListener(revertFileInVcsButtonListener);
        add(revert);

        refreshGitTree.addActionListener(refreshVcsTreeButtonListener);
        add(refreshGitTree);
    }
}