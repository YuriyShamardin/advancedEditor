package com.shamardin.advancededitor;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.swing.*;


public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
            context.getBean(Editor.class).start();
        });
    }
}
