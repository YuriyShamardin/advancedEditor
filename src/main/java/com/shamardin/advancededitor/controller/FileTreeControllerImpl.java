package com.shamardin.advancededitor.controller;

import com.shamardin.advancededitor.core.fileloading.FileProcessor;
import com.shamardin.advancededitor.view.FileContentTab;
import com.shamardin.advancededitor.view.FileTreePanel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static com.shamardin.advancededitor.core.PathUtil.getFileWithRelativePath;
import static org.apache.commons.lang3.ArrayUtils.nullToEmpty;

@Slf4j
@Component
public class FileTreeControllerImpl implements FileTreeController {
    @Autowired
    private FileProcessor fileProcessor;

    @Autowired
    private FileTreePanel fileTreePanel;

    @Autowired
    private FileContentTab fileContentTab;

    @Getter
    private List<File> fileList = new ArrayList<>();


    @Override
    public void showFileTree(File file) {
        fileTreePanel.showTree(buildTree(null, file, file.getPath()));
    }

    private DefaultMutableTreeNode buildTree(DefaultMutableTreeNode curTop, File dir, String relativePath) {
        String curPath = dir.getPath();
        DefaultMutableTreeNode curDir = new DefaultMutableTreeNode(relativePath);
        if(curTop != null) { // for root
            curTop.add(curDir);
        }
        String[] fileNames = nullToEmpty(dir.list());

        Vector<String> filePath = new Vector<>();
        // Make two passes, one for Dirs and one for Files. This is #1.
        for (String thisObject : fileNames) {
            String fullPath = curPath + File.separator + thisObject;

            File f = new File(fullPath);
            if(f.isDirectory()) {
                buildTree(curDir, f, thisObject);
            } else {
                if(!fullPath.contains("\\.")) {
                    fileList.add(getFileWithRelativePath(fullPath));
                }
                filePath.addElement(thisObject);
            }
        }
        // Pass two: for files.
        for (String aFilePath : filePath) {
            curDir.add(new DefaultMutableTreeNode(aFilePath));
        }
        return curDir;
    }
}

