package com.shamardin.advancededitor.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;

import static javax.swing.JSplitPane.HORIZONTAL_SPLIT;
import static javax.swing.JSplitPane.VERTICAL_SPLIT;

@Component
public class MainPanel extends JPanel {

    @Autowired
    private FileTreePanel fileTreePanel;

    @Autowired
    private ButtonsPanel buttonsPanel;

    @Autowired
    private GitFilesListPanel gitFilesListPanel;

    @Autowired
    private UntrackFilePanel untrackFilePanel;

    @Autowired
    private FileContentTab fileContentTab;

    @PostConstruct
    public void init() {
        JSplitPane filesInfoSplitPane = new JSplitPane(HORIZONTAL_SPLIT);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        filesInfoSplitPane.setOneTouchExpandable(false);
        filesInfoSplitPane.setLeftComponent(fileTreePanel);
        filesInfoSplitPane.setRightComponent(fileContentTab);
        filesInfoSplitPane.setAlignmentX(LEFT_ALIGNMENT);

        JPanel gitPanel = new JPanel();
        gitPanel.setLayout(new BoxLayout(gitPanel, BoxLayout.Y_AXIS));
        gitPanel.add(new JSeparator());
        buttonsPanel.setAlignmentX(LEFT_ALIGNMENT);
        gitPanel.add(buttonsPanel);

        JSplitPane gitInfoSplitPane = new JSplitPane(VERTICAL_SPLIT);
        gitFilesListPanel.setAlignmentX(LEFT_ALIGNMENT);
        gitInfoSplitPane.setTopComponent(gitFilesListPanel);
        untrackFilePanel.setAlignmentX(LEFT_ALIGNMENT);
        gitInfoSplitPane.setBottomComponent(untrackFilePanel);

        gitPanel.add(gitInfoSplitPane);
        JSplitPane centerSplitPane = new JSplitPane(VERTICAL_SPLIT);
        centerSplitPane.setTopComponent(filesInfoSplitPane);
        centerSplitPane.setBottomComponent(gitPanel);

        add(centerSplitPane);
    }
}
