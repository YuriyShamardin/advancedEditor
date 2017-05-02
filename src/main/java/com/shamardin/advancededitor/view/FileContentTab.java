package com.shamardin.advancededitor.view;

import com.shamardin.advancededitor.controller.FileTreeController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.tree.TreePath;
import java.awt.*;

import static com.shamardin.advancededitor.PathUtil.getFileWithRelativePath;

@Slf4j
@Component
public class FileContentTab extends JTabbedPane {
    @Autowired
    FileTreeController fileTreeController;

    @PostConstruct
    public void init() {
        setPreferredSize(new Dimension(200, 400));
        setMinimumSize(new Dimension(100, 400));
        addChangeListener(fileTreeController);
    }
}
