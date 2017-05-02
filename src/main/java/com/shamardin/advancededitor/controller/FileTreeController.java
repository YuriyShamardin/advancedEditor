package com.shamardin.advancededitor.controller;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

public interface FileTreeController extends TreeSelectionListener, ChangeListener {

    void showFileTree(File file);

    void valueChanged(TreeSelectionEvent e);

    List<File> getFileList();


    /**
     * When change selected tab
     */
     void stateChanged(ChangeEvent e);
}
