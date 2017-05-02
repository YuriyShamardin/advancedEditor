package com.shamardin.advancededitor.controller;

import java.io.File;

public interface VCSController {
    void openRepository(File file);

    void addFileToVcs(File file);

    void updateGitPanel();

    void refreshGitPanel();

    void removeFile(String fileName);
}
