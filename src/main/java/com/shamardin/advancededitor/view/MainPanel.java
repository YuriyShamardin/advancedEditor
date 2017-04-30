package com.shamardin.advancededitor.view;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;

import static lombok.AccessLevel.PACKAGE;

@Component
public class MainPanel extends JPanel {

    @Getter(PACKAGE)
    @Autowired
    private FileContentArea fileContentArea;

    @Autowired
    private JSplitPane splitPane;

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
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        splitPane.setOneTouchExpandable(false);
        splitPane.setLeftComponent(fileTreePanel);
//        splitPane.setRightComponent(new JScrollPane(fileContentArea));
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
