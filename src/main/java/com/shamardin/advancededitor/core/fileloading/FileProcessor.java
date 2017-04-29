package com.shamardin.advancededitor.core.fileloading;

import java.io.File;

public interface FileProcessor {

    byte[] load(File file);

    boolean isLoaded(File file);
}
