package com.shamardin.advancededitor.listener;

import com.shamardin.advancededitor.controller.FileContentController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import java.io.File;

import static java.io.File.separator;

@Slf4j
@Component
public class FileTreeSelectionListener implements TreeSelectionListener {
    @Autowired
    private FileContentController fileContentController;

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        TreePath path = e.getPath();
        String fullPath = StringUtils.join(path.getPath(), separator);

        log.debug("Selected {}", fullPath);
        File file = new File(fullPath);
        if(file.isFile()) {
            fileContentController.showFile(file);
        }
    }
}