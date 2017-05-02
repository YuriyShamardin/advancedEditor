package com.shamardin.advancededitor.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;

import static javax.swing.JSplitPane.HORIZONTAL_SPLIT;

@Component
public class MainPanel extends JPanel {

    @Autowired
    private FileTreePanel fileTreePanel;

    @Autowired
    private ButtonsPanel buttonsPanel;

    @Autowired
    private GitFilesListPanel gitFilesListPanel;

    @Autowired
    private FileContentTab fileContentTab;

    @PostConstruct
    public void init() {
        JSplitPane splitPane = new JSplitPane(HORIZONTAL_SPLIT);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        splitPane.setOneTouchExpandable(false);
        splitPane.setLeftComponent(fileTreePanel);

        splitPane.setRightComponent(fileContentTab);

        splitPane.setAlignmentX(LEFT_ALIGNMENT);
        add(splitPane);

        add(new JSeparator());
        buttonsPanel.setAlignmentX(LEFT_ALIGNMENT);
        add(buttonsPanel);
        add(new JSeparator());

        gitFilesListPanel.setAlignmentX(LEFT_ALIGNMENT);
        add(gitFilesListPanel);
    }
}
