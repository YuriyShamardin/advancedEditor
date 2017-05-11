package com.shamardin.advancededitor.listener;

import com.shamardin.advancededitor.view.FileContentTab;
import com.shamardin.advancededitor.view.FileTreePanel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.Position;
import javax.swing.tree.TreePath;

import static com.shamardin.advancededitor.core.PathUtil.getFileWithRelativePath;
import static java.io.File.separator;
import static java.util.regex.Matcher.quoteReplacement;

@Slf4j
@Component
public class SelectTabListener implements ChangeListener {
    @Autowired
    private FileContentTab fileContentTab;
    @Autowired
    private FileTreePanel fileTreePanel;

    /**
     * When change selected tab
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        int selectedComponent = fileContentTab.getSelectedIndex();
        //if removed last tab
        if(selectedComponent != -1) {
            String fileNameFromTitle = getFileWithRelativePath(fileContentTab.getTitleAt(selectedComponent)).getPath();
            String[] piecesOfPath = fileNameFromTitle.split(quoteReplacement(separator));
            JTree fileTree = fileTreePanel.getFileTree();

            //resolve two file with same names
            TreePath nextMatch = fileTree.getNextMatch(piecesOfPath[0], 0, null);
            for(String pieceOfPath : piecesOfPath) {
                nextMatch = fileTree.getNextMatch(pieceOfPath, fileTree.getRowForPath(nextMatch), Position.Bias.Forward);
            }
            fileTree.setSelectionRow(fileTree.getRowForPath(nextMatch));
        }
    }
}

