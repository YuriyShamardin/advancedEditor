package com.shamardin.advancededitor.core.git;

import java.io.File;
import java.util.List;

public interface VcsProcessor {
    boolean openRepository(File file);

    boolean createRepository(File file);

    List<String> getAllUntrackedFiles();

    FileStatus computeFileStatus(File file);

    void addFile(File file);

    void removeFile(File file);

    void revertFile(File file);

    List<String> getAllRemovedFiles();
}
