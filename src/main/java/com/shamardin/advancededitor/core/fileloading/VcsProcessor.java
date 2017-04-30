package com.shamardin.advancededitor.core.fileloading;

import org.eclipse.jgit.lib.Repository;

import java.io.File;

public interface VcsProcessor {
    Repository openRepository(File file);
}
