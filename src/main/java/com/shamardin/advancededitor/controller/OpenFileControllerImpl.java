package com.shamardin.advancededitor.controller;

import com.shamardin.advancededitor.core.fileloading.FileProcessor;
import com.shamardin.advancededitor.view.FileTreePanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;
import java.util.Vector;

import static org.apache.commons.lang3.ArrayUtils.nullToEmpty;

@Component
public class OpenFileControllerImpl implements OpenFileController {

//    @Autowired
//    private FilesListPanel filesListPanel;

    @Autowired
    private FileProcessor fileProcessor;

    @Autowired
    private FileTreePanel fileTreePanel;

//    @Override
//    public void showFileInTextArea(File file) {
//        if(fileProcessor.isLoaded(file)) {
//            filesListPanel.chooseFileInList(file);
//        } else {
//            filesListPanel.addFileInList(file);
//        }
//    }

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

        File f;
        Vector<String> filePath = new Vector<>();
        // Make two passes, one for Dirs and one for Files. This is #1.
        for (String thisObject : fileNames) {
            String fullPath = curPath + File.separator + thisObject;

            if((f = new File(fullPath)).isDirectory()) {
                buildTree(curDir, f, thisObject);
            } else {
                filePath.addElement(thisObject);
            }
        }
        // Pass two: for files.
        for (int fnum = 0; fnum < filePath.size(); fnum++) {
            curDir.add(new DefaultMutableTreeNode(filePath.elementAt(fnum)));
        }
        return curDir;
    }
}
