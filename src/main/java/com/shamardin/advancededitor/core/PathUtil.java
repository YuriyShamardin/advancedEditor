package com.shamardin.advancededitor.core;

import java.io.File;

public class PathUtil {

    private static volatile String root = "";

    private PathUtil() {
    }

    public synchronized static void setRoot(String root) {
        PathUtil.root = root + "\\";
    }

    public synchronized static String getRoot() {
        return root.substring(0, root.length() - 1);
    }

    /**
     * Return file with relative path. Remove root path from full path.
     * Note! Before using set root path.
     */
    public synchronized static File getFileWithRelativePath(String fullPath) {
        return new File(fullPath.replace(root, ""));
    }

    /**
     * Return file with relative path. Remove root path from full path.
     * Note! Before using set root path.
     */
    public synchronized static File getFileWithRelativePath(File fileWithFullPath) {
        return new File(fileWithFullPath.getPath().replace(root, ""));
    }

    /**
     * replace all '\' symbols to '/'
     */
    public synchronized static String getGitFriendlyPath(String path) {
        return path.replace("\\", "/");
    }
}
