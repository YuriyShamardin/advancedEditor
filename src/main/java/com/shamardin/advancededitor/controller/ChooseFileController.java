package com.shamardin.advancededitor.controller;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import java.io.File;

public interface ChooseFileController extends ListSelectionListener, TreeSelectionListener {
    void chooseFile(File file);

    @Override
    void valueChanged(ListSelectionEvent e);
    @Override
    void valueChanged(TreeSelectionEvent e);
}
