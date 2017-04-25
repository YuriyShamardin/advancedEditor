package com.shamardin.advancededitor.view.component;

import org.springframework.stereotype.Component;

import javax.swing.*;

import static javax.swing.SwingConstants.HORIZONTAL;

//@Component
public class FilesListPanel extends JPanel {

    public FilesListPanel() {
        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);
        add(new JLabel("FileList"));
        add(new JSeparator(HORIZONTAL));
    }
}
