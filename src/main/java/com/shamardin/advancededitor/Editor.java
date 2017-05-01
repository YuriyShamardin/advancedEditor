package com.shamardin.advancededitor;

import com.shamardin.advancededitor.view.MainMenu;
import com.shamardin.advancededitor.view.MainPanel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.awt.*;

import static lombok.AccessLevel.PACKAGE;

@Slf4j
public class Editor extends JFrame {

    @Getter(PACKAGE)
    @Autowired
    private MainPanel mainPanel;

    @Autowired
    private MainMenu menuBar;

    public void start() throws HeadlessException {
        setLayout(new BorderLayout());

        setJMenuBar(menuBar);
        add(mainPanel);

        setTitle("Advanced editor");
        setLocationRelativeTo(null);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
