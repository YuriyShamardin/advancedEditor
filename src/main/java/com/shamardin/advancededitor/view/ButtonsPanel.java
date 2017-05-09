package com.shamardin.advancededitor.view;

import com.shamardin.advancededitor.listener.*;
import lombok.Getter;
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
    @Autowired
    private SaveFileOnDiskListener saveFileOnDiskListener;

    @Getter
    private final JButton refreshGitTree = new JButton("Refresh");
    @Getter
    private final JButton revert = new JButton("Revert");
    @Getter
    private final JButton removeFromGit = new JButton("Remove");
    @Getter
    private final JButton addToGit = new JButton("Add to git");
    @Getter
    private final JButton saveFile = new JButton("Save changes on disk");

    @PostConstruct
    public void init() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(new JLabel("Buttons"));

        addToGit.addActionListener(addFileToVcsButtonListener);
        addToGit.setEnabled(false);
        add(addToGit);

        removeFromGit.addActionListener(removeFromVcsButtonListener);
        removeFromGit.setEnabled(false);
        add(removeFromGit);

        revert.addActionListener(revertFileInVcsButtonListener);
        revert.setEnabled(false);
        add(revert);

        refreshGitTree.addActionListener(refreshVcsTreeButtonListener);
        refreshGitTree.setEnabled(true);
        add(refreshGitTree);

        saveFile.addActionListener(saveFileOnDiskListener);
        saveFile.setEnabled(true);
        add(saveFile);
    }
}