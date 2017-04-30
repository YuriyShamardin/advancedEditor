package com.shamardin.advancededitor.core.git;

import java.io.File;
import java.util.Set;

public interface VcsProcessor {
    boolean openRepository(File file);

    boolean createRepository(File file);

    void updateStatus();

    Set<String> getUntrackedFiles();


    Set<String> addFile(File file);
}
