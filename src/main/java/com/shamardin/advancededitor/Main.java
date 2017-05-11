package com.shamardin.advancededitor;

import de.javasoft.plaf.synthetica.SyntheticaAluOxideLookAndFeel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.swing.*;
import java.text.ParseException;

@Slf4j
public class Main {

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(new SyntheticaAluOxideLookAndFeel());
        } catch(UnsupportedLookAndFeelException | ParseException e) {
            log.info("Look and feel error \n {} \n Try to use default.", e);

            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch(ClassNotFoundException |
                    InstantiationException |
                    IllegalAccessException |
                    UnsupportedLookAndFeelException e1) {
                log.info("Load of system look and feel error \n {}", e1);
            }
        }

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        SwingUtilities.invokeLater(() -> context.getBean(Editor.class).start());
    }
}