package com.shamardin.advancededitor.listener;

import com.shamardin.advancededitor.view.FileContentTab;
import com.shamardin.advancededitor.view.FileTreePanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import static com.shamardin.advancededitor.PathUtil.getFileWithRelativePath;

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
        String fileNameFromTitle = getFileWithRelativePath(fileContentTab.getTitleAt(selectedComponent)).getPath();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) fileTreePanel.getFileTree().getModel().getRoot();
        String[] split = fileNameFromTitle.split("\\\\");

        // TODO: 02-May-17 Bad solution!!!!
        TreePath nextMatch = fileTreePanel.getFileTree().getNextMatch(split[split.length - 1], 0, null);
        fileTreePanel.getFileTree().setSelectionPath(nextMatch);
    }
}

