package com.shamardin.advancededitor.controller;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import java.io.File;
import java.util.List;

public interface FileTreeController extends TreeSelectionListener {

    void showFileTree(File file);

    void valueChanged(TreeSelectionEvent e);

    List<File> getFileList();

    String getRootPath();
}
