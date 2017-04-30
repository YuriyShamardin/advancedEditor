package com.shamardin.advancededitor.view;

import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class FileContentArea extends JTextArea {
    public FileContentArea() {
        setLineWrap(true);
    }

    public Dimension getMinimumSize() {

        return new Dimension(100, 400);
    }

    public Dimension getPreferredSize() {
        return new Dimension(200, 400);
    }
}
