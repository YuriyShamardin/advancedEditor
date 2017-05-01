package com.shamardin.advancededitor.core.git;

import java.io.File;

public interface VcsProcessor {
    boolean openRepository(File file);

    boolean createRepository(File file);

    FileStatus computeFileStatus(File file);

    void addFile(File file);
}
