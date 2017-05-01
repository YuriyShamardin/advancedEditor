package com.shamardin.advancededitor.controller;

import com.shamardin.advancededitor.core.fileloading.FileProcessor;
import com.shamardin.advancededitor.view.ButtonTabComponent;
import com.shamardin.advancededitor.view.FileContentArea;
import com.shamardin.advancededitor.view.FileContentTab;
import com.shamardin.advancededitor.view.LoadingFileWaitDialog;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.util.List;

@Slf4j
@Component
public class FileContentControllerImpl implements FileContentController {
    public static final int TAB_NOT_FOUND = -1;
    @Autowired
    private FileProcessor fileProcessor;

    @Autowired
    private FileContentTab fileContentTab;

    @Autowired
    private LoadingFileWaitDialog waitDialog;

    @Autowired
    private FileChangeController fileChangeController;

    @Autowired
    VCSController vcsController;

    private volatile TextShower shower;

    @Override
    public synchronized void showFile(File file) {
        if((shower != null) && (!shower.isDone())) {
            shower.cancel(true);
        }
        String content = fileProcessor.loadFileInContainer(file);

        int indexOfTab = fileContentTab.indexOfTab(file.getName());
        if(indexOfTab == TAB_NOT_FOUND) {
            indexOfTab = createNewTab(file);
        }

        JScrollPane scrollPane = (JScrollPane) fileContentTab.getComponentAt(indexOfTab);
        FileContentArea fileContentArea = (FileContentArea) scrollPane.getViewport().getView();
        fileContentTab.setSelectedIndex(indexOfTab);

        shower = new TextShower();
        shower.showTextInTextArea(content, fileContentArea);
    }

    private int createNewTab(File file) {
        int indexOfTab;

        FileContentArea fileContentArea = new FileContentArea();
        fileContentArea.addKeyListener(fileChangeController);

        fileContentTab.addTab(file.getName(), new JScrollPane(fileContentArea));
        fileContentTab.setTabComponentAt(fileContentTab.getTabCount() - 1,
                new ButtonTabComponent(fileContentTab, vcsController));
        indexOfTab = fileContentTab.indexOfTab(file.getName());
        return indexOfTab;
    }

    //Show big text fast
    private class TextShower extends SwingWorker<String, String> {
        private String content;
        private FileContentArea fileContentArea;

        private void showTextInTextArea(String content, FileContentArea fileContentArea) {
            this.content = content;
            this.fileContentArea = fileContentArea;
            execute();
        }

        @Override
        @SneakyThrows
        protected String doInBackground() {
            fileContentArea.setText("");
            try (BufferedReader stream = new BufferedReader(
                    new InputStreamReader(new ByteArrayInputStream(content.getBytes())))) {
                String line;
                while ((line = stream.readLine()) != null) {
                    if(isCancelled()) {
                        return "";
                    }
                    publish(line);
                }
                return "Done";
            }
        }

        @Override
        protected void process(List<String> chunks) {
            StringBuilder buffer = new StringBuilder();
            for (String line : chunks) {
                buffer.append(line)
                        .append("\n");
                if(isCancelled()) {
                    return;
                }
            }
            fileContentArea.append(buffer.toString());
        }

        @Override
        protected void done() {
//            waitDialog.close();
        }

    }
}
