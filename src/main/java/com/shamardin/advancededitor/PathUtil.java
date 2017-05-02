package com.shamardin.advancededitor;

import java.io.File;

public class PathUtil {

    private static String root = "";

    public static void setRoot(String root) {
        PathUtil.root = root + "\\";
    }

    /**
     * Return file with relative path. Remove root path from full path.
     * Note! Before using set root path.
     */
    public static File getFileWithRelativePath(String fullPath) {
        return new File(fullPath.replace(root, ""));
    }

    /**
     * Return file with relative path. Remove root path from full path.
     * Note! Before using set root path.
     */
    public static File getFileWithRelativePath(File fileWithFullPath) {
        return new File(fileWithFullPath.getPath().replace(root, ""));
    }

    /**
     * replace all "\" symbols to /
     */
    public static String getGitFriendlyPath(String path) {
        return path.replace("\\", "/");
    }
}
