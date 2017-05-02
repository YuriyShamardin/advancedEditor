package com.shamardin.advancededitor.listener;

import com.shamardin.advancededitor.view.FileContentTab;
import com.shamardin.advancededitor.view.FileTreePanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.TreePath;

import static com.shamardin.advancededitor.core.PathUtil.getFileWithRelativePath;

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
            String[] split = fileNameFromTitle.split("\\\\");
            TreePath nextMatch = fileTreePanel.getFileTree().getNextMatch(split[split.length - 1], 0, null);
            fileTreePanel.getFileTree().setSelectionPath(nextMatch);
        }
    }
}

