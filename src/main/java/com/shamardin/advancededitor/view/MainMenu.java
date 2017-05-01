package com.shamardin.advancededitor.view;


import com.shamardin.advancededitor.controller.GitController;
import com.shamardin.advancededitor.controller.FileTreeController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.io.File;

import static javax.swing.JFileChooser.APPROVE_OPTION;
import static javax.swing.JFileChooser.DIRECTORIES_ONLY;

@Component
public class MainMenu extends JMenuBar {
    @Autowired
    private FileTreeController fileTreeController;

    @Autowired
    private GitController gitController;

    @PostConstruct
    public void init() {
        JMenu mainTab = new JMenu("Menu");

        JMenuItem openFile = new JMenuItem("Open file");
        mainTab.add(openFile);
        openFile.addActionListener(e ->
        {
            JFileChooser fileopen = new JFileChooser();
            fileopen.setFileSelectionMode(DIRECTORIES_ONLY);
            int ret = fileopen.showDialog(null, "Открыть файл");
            if(ret == APPROVE_OPTION) {
                File file = fileopen.getSelectedFile();
                fileTreeController.showFileTree(file);
                gitController.openRepository(file);
            }
        });
        JMenuItem exit = new JMenuItem("Exit");
        mainTab.add(exit);
        exit.addActionListener(e -> System.exit(0));

        add(mainTab);
    }
}
