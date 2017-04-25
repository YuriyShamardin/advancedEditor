package com.shamardin.advancededitor.view.component;

import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

//@Component
public class MainPanel extends JPanel {

    private FilesListPanel filesListPanel;
    @Getter
    private FileContentArea fileContentArea;
    private JSplitPane splitPane;

    public MainPanel() {
        setLayout(new BorderLayout());
        filesListPanel = new FilesListPanel();
        fileContentArea = new FileContentArea();

        Dimension minimumSize = new Dimension(100, 30);

        filesListPanel.setMinimumSize(minimumSize);
        fileContentArea.setMinimumSize(minimumSize);

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        splitPane.setLeftComponent(filesListPanel);
        splitPane.setRightComponent(new JScrollPane(fileContentArea));

        add(splitPane);
    }
}
