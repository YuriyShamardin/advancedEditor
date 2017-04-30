package com.shamardin.advancededitor.core.fileloading;

import java.io.File;

public interface FileProcessor {
    String loadFileInCache(File file);

    String readFile(File file);

    boolean isLoaded(File file);

}
