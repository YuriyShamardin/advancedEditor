package com.shamardin.advancededitor;

import com.shamardin.advancededitor.view.component.MainMenu;
import com.shamardin.advancededitor.view.component.MainPanel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

@Slf4j
//@Component
public class Editor extends JFrame {

    @Getter
//    @Autowired
    private MainPanel mainPanel;
//    @Autowired
    private MainMenu menuBar;

    public Editor() throws HeadlessException {
        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException |
                ClassNotFoundException |
                InstantiationException |
                IllegalAccessException e) {
            log.info(e.getMessage(), e); //OK, using default style
        }
        setLayout(new BorderLayout());

//        mainPanel = new MainPanel();
//        menuBar = new MainMenu();

        setJMenuBar(menuBar);
        add(mainPanel);

        setTitle("Advanced editor");
        setLocationRelativeTo(null);
        setSize(500, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

   /* @PostConstruct
    public void run(){
        SwingUtilities.invokeLater(Editor::new);
    }*/


}
