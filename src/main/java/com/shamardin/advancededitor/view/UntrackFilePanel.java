package com.shamardin.advancededitor.view;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.io.File;

import static java.awt.Color.RED;
import static javax.swing.ListSelectionModel.SINGLE_SELECTION;

@Component
public class UntrackFilePanel extends JPanel {

    private DefaultListModel<File> listModel;
    private JList<File> view;

    @PostConstruct
    public void init() {
        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);
        add(new JLabel("Unversioned files"));
        listModel = new DefaultListModel<>();

        view = new JList<>(listModel);
        view.setSelectionMode(SINGLE_SELECTION);
        CellRender cellRenderer = new CellRender();
        view.setCellRenderer(cellRenderer);
        add(new JScrollPane(view));
    }

    public void addFileInList(File file) {
        if(!listModel.contains(file)) {
            listModel.add(listModel.size(), file);
        }
    }

    public void clearUntrackFileList() {
        listModel.clear();
    }

    private class CellRender extends DefaultListCellRenderer {
        @Override
        public java.awt.Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            setEnabled(false);
            JLabel label = new JLabel();
            label.setForeground(RED);
            label.setText(value.toString());
            return label;
        }
    }
}
