package com.shamardin.advancededitor.controller;

import java.io.File;

public interface VCSController {
    void openRepository(File file);

    void addFileToVcs(File file);

    /**
     * Update status of files from VCS and refresh panel
     */
    void updateGitPanel();

    /**
     * Refresh panel without refreshing status of files from VCS
     */
    void refreshGitPanel();

    void removeFromVcs(File file);

    void revert(File file);
}
