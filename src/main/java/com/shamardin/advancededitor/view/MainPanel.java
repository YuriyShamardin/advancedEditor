package com.shamardin.advancededitor.view;

import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

import static lombok.AccessLevel.*;

@Component
public class MainPanel extends JPanel {

    @Getter(PACKAGE)
    @Autowired
    private FilesListPanel filesListPanel;

    @Getter(PACKAGE)
    @Autowired
    private FileContentArea fileContentArea;

    @Autowired
    private JSplitPane splitPane;

    @PostConstruct
    public void init() {
        setLayout(new BorderLayout());

        Dimension minimumSize = new Dimension(100, 30);
        filesListPanel.setMinimumSize(minimumSize);
//        filesListPanel.setPreferredSize(minimumSize);
        fileContentArea.setMinimumSize(minimumSize);
//        fileContentArea.setPreferredSize(minimumSize);

        splitPane.setLeftComponent(filesListPanel);
        splitPane.setRightComponent(new JScrollPane(fileContentArea));

        add(splitPane);
    }
}
