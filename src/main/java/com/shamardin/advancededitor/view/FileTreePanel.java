package com.shamardin.advancededitor.view;

import com.shamardin.advancededitor.controller.FileTreeController;
import com.shamardin.advancededitor.listener.FileTreeSelectionListener;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;

import static java.awt.BorderLayout.CENTER;
import static java.io.File.separator;

@Slf4j
@Component
public class FileTreePanel extends JPanel {
    @Autowired
    private FileTreeController fileTreeController;

    @Autowired
    private FileTreeSelectionListener fileTreeSelectionListener;

    @Getter
    private JTree fileTree;
    private JScrollPane scrollpane = new JScrollPane();

    @PostConstruct
    public void init() {
        setLayout(new BorderLayout());
        fileTree = new JTree();
        add(CENTER, scrollpane);
        setMinimumSize(new Dimension(100, 400));
        setPreferredSize(new Dimension(200, 400));

        // Add a listener
        fileTree.addTreeSelectionListener(fileTreeSelectionListener);
    }

    public void showTree(DefaultMutableTreeNode treeNode) {
        fileTree.setModel(new DefaultTreeModel(treeNode));
        scrollpane.getViewport().add(fileTree);
    }

    public synchronized String getSelectedFilePath() {
        TreePath path = fileTree.getSelectionPath();
        return StringUtils.join(path.getPath(), separator);
    }
}
