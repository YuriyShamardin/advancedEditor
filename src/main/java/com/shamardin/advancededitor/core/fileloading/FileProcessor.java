package com.shamardin.advancededitor.core.fileloading;

import java.io.File;
import java.util.List;

public interface FileProcessor {

    /**
     * If file does't open, read from disk and add as tracked.
     * @param file
     * @return fil content
     */
    String getFileContent(File file);

    boolean isFileTracked(File file);

    void unTrackFile(File file);

    List<File> getAllTrackedFiles();
}
