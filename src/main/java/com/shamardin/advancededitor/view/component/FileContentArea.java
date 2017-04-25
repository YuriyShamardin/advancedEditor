package com.shamardin.advancededitor.view.component;

import org.springframework.stereotype.Component;

import javax.swing.*;

//@Component
public class FileContentArea extends JTextArea {
    public FileContentArea() {
        setLineWrap(true);
    }
}
