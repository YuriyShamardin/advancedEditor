package com.shamardin.advancededitor.controller;

import javax.swing.event.ListSelectionListener;
import java.io.File;

public interface ChooseFileController extends ListSelectionListener {
    void chooseFile(File file);
}
