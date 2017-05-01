package com.shamardin.advancededitor.view;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Slf4j
@Component
public class FileContentTab extends JTabbedPane {
    public FileContentTab() {
        setPreferredSize(new Dimension(200, 400));
        setMinimumSize(new Dimension(100, 400));
    }
}
