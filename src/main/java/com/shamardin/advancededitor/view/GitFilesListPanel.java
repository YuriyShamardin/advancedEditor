package com.shamardin.advancededitor.view;

import com.shamardin.advancededitor.core.git.FileStatusContainer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;
import java.io.File;

import static javax.swing.ListSelectionModel.SINGLE_SELECTION;

@Slf4j
@org.springframework.stereotype.Component
public class GitFilesListPanel extends JPanel {

    @Autowired
    private FileStatusContainer fileStatusContainer;

    private DefaultListModel<File> listModel;
    private JList<File> view;

    @PostConstruct
    public void init() {
        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);
        add(new JLabel("Changes View"));
        listModel = new DefaultListModel<>();

        view = new JList<>(listModel);
        view.setSelectionMode(SINGLE_SELECTION);
        CellRender cellRenderer = new CellRender();
        view.setCellRenderer(cellRenderer);
        view.setSelectionForeground(Color.RED);
        add(new JScrollPane(view));
    }

    public void addFileInList(File file) {
        if(!listModel.contains(file)) {
            listModel.add(listModel.size(), file);
        }
    }

    public File getSelectedFile() {
        return view.getSelectedValue();
    }

    public void removeFileFromList(String fileName) {
        if(listModel.removeElement(fileName)) {
            log.info("Success delete file \"{}\" from list", fileName);
        } else {
            log.info("Can not delete file \"{}\" from list. File not found", fileName);
        }
    }

    public void clearGitFileList() {
        listModel.removeAllElements();
    }

    private class CellRender extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            component.setBackground(fileStatusContainer.getFileColor((File) value));
            return component;
        }
    }
}
