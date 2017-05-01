package com.shamardin.advancededitor.core.fileloading;

import java.io.File;
import java.util.List;

public interface FileProcessor {
    String loadFileInContainer(File file);

//    String readFile(File file);

    boolean isLoaded(File file);

    // TODO: 01-May-17 Silly name
    void removeFile(File file);

    List<File> getAllTrackedFiles();
}
