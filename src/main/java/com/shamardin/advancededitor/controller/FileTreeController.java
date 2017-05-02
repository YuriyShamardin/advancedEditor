package com.shamardin.advancededitor.controller;

import java.io.File;
import java.util.List;

public interface FileTreeController {

    void showFileTree(File file);

    List<File> getFileList();


}
