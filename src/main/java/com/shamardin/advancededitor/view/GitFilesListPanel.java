package com.shamardin.advancededitor.view;

import com.shamardin.advancededitor.controller.MouseController;
import lombok.Setter;
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
    private MouseController mouseController;

    private DefaultListModel<File> listModel;
    private JList<File> view;
    private CellRender cellRenderer;

    @PostConstruct
    public void init() {
        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);
        add(new JLabel("Changes View"));
        listModel = new DefaultListModel<>();

        view = new JList<>(listModel);
        view.setSelectionMode(SINGLE_SELECTION);
        cellRenderer = new CellRender();
        view.setCellRenderer(cellRenderer);
        view.setSelectionForeground(Color.RED);
        view.addMouseListener(mouseController);
        add(new JScrollPane(view));
    }

    public void addFileInList(File file, Color color) {
        cellRenderer.setColor(color);
        listModel.add(listModel.size(), file);
    }

    public void chooseFileInList(File file) {
        view.setSelectedValue(file, true);
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
        @Setter
        private Color color;

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            setBackground(color);
            return this;
        }
    }
}
