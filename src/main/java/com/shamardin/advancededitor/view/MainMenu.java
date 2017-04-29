package com.shamardin.advancededitor.view;


import com.shamardin.advancededitor.controller.OpenFileController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.io.File;

@Component
public class MainMenu extends JMenuBar {
    @Autowired
    private OpenFileController openFileController;

    @PostConstruct
    public void init() {
        JMenu mainTab = new JMenu("Menu");

        JMenuItem openFile = new JMenuItem("Open file");
        mainTab.add(openFile);
        openFile.addActionListener(e ->
        {
            JFileChooser fileopen = new JFileChooser();
            int ret = fileopen.showDialog(null, "Открыть файл");
            if(ret == JFileChooser.APPROVE_OPTION) {
                File file = fileopen.getSelectedFile();
                openFileController.showFileInTextArea(file);
            }
        });
        JMenuItem exit = new JMenuItem("Exit");
        mainTab.add(exit);
        exit.addActionListener(e -> System.exit(0));

        add(mainTab);
    }
}
