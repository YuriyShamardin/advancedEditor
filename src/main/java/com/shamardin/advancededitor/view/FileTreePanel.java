package com.shamardin.advancededitor.view;

import com.shamardin.advancededitor.controller.ChooseFileController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;

import static java.awt.BorderLayout.CENTER;

@Slf4j
@Component
public class FileTreePanel extends JPanel {
    @Autowired
    private ChooseFileController chooseFileController;

    private JTree tree;
    private JScrollPane scrollpane = new JScrollPane();

    @PostConstruct
    public void init() {
        setLayout(new BorderLayout());
        tree = new JTree();
        add(CENTER, scrollpane);

        // Add a listener
        tree.addTreeSelectionListener(chooseFileController);
    }

    public void showTree(DefaultMutableTreeNode treeNode) {
        tree.setModel(new DefaultTreeModel(treeNode));
        scrollpane.getViewport().add(tree);
    }

    public Dimension getMinimumSize() {
        return new Dimension(100, 400);
    }

    public Dimension getPreferredSize() {
        return new Dimension(200, 400);
    }
}
