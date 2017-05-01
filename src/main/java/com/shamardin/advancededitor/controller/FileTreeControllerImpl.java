package com.shamardin.advancededitor.controller;

import com.shamardin.advancededitor.core.fileloading.FileProcessor;
import com.shamardin.advancededitor.view.FileTreePanel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static java.io.File.separator;
import static org.apache.commons.lang3.ArrayUtils.nullToEmpty;

@Slf4j
@Component
public class FileTreeControllerImpl implements FileTreeController {
    @Autowired
    private FileProcessor fileProcessor;

    @Autowired
    private FileTreePanel fileTreePanel;

    @Autowired
    private FileContentController fileContentController;

    @Getter
    private List<File> fileList = new ArrayList<>();

    @Getter
    private String rootPath;

    @Override
    public void showFileTree(File file) {
        rootPath = file.getPath() + "\\";
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
                if(!fullPath.contains("\\.")) {
                    fileList.add(new File(fullPath.replace(rootPath, "")));
                }
                filePath.addElement(thisObject);
            }
        }
        // Pass two: for files.
        for (int fnum = 0; fnum < filePath.size(); fnum++) {
            curDir.add(new DefaultMutableTreeNode(filePath.elementAt(fnum)));
        }
        return curDir;
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        TreePath path = e.getPath();
        String fullPath = StringUtils.join(path.getPath(), separator);

        log.info("Selected {}", fullPath);
        File file = new File(fullPath);
        if(file.isFile()) {
            fileContentController.showFile(file);
        }
    }
}
