package com.shamardin.advancededitor.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

@Slf4j
@Component
public class MouseController implements MouseListener {
    private static final int RIGHT_BUTTON = 3;

    @Override
    @SuppressWarnings("unchecked")
    public void mouseClicked(MouseEvent e) {
        if(RIGHT_BUTTON == e.getButton()) {
            JList<File> fileList = (JList<File>) e.getSource();
            int index = fileList.locationToIndex(e.getPoint());
            if(index >= 0) {
                File o = fileList.getModel().getElementAt(index);
                log.info("clicked on: " + o.toString());
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
