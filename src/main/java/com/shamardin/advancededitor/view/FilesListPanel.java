package com.shamardin.advancededitor.view;

import com.shamardin.advancededitor.controller.ChooseFileController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;

import java.io.File;

import static javax.swing.ListSelectionModel.SINGLE_SELECTION;
import static javax.swing.SwingConstants.HORIZONTAL;

@Component
@Slf4j
public class FilesListPanel extends JPanel {
    @Autowired
    private ChooseFileController chooseFileController;

    private DefaultListModel<File> listModel;

    @PostConstruct
    public void init() {
        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);
        add(new JLabel("FileList"));
        listModel = new DefaultListModel<>();

        JList<File> view = new JList<>(listModel);
        view.setSelectionMode(SINGLE_SELECTION);

        view.addListSelectionListener(chooseFileController);
        add(new JScrollPane(view));
    }

    public void addFileInList(File file){
//        listModel.add(listModel.size(),file.getName()+"\n"+file.getAbsolutePath());
        listModel.add(listModel.size(),file);
    }

    public void removeFileFromList(String fileName){
        if(listModel.removeElement(fileName)){
            log.info("Success delete file \"{}\" from list",fileName);
        }else {
            log.info("Can not delete file \"{}\" from list. File not found",fileName);
        }
    }
}
