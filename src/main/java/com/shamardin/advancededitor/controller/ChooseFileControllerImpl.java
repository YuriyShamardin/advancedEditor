package com.shamardin.advancededitor.controller;

import com.shamardin.advancededitor.core.fileloading.FileProcessor;
import com.shamardin.advancededitor.view.LoadingFileWaitDialog;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreePath;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.util.List;

import static java.io.File.separator;

@Slf4j
@Component
public class ChooseFileControllerImpl implements ChooseFileController {
    @Autowired
    private FileProcessor fileProcessor;
    @Autowired
    @Qualifier("fileContentArea")
    private JTextArea fileContentArea;
    @Autowired
    private LoadingFileWaitDialog waitDialog;

    private TextShower shower;


    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(!e.getValueIsAdjusting()) {
            File file = (File) ((JList) e.getSource()).getSelectedValue();
            log.info("choose file {}", file);
            chooseFile(file);
        }
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        TreePath path = e.getPath();
        String fullPath = StringUtils.join(path.getPath(), separator);

        log.info("Selected {}", fullPath);
        File file = new File(fullPath);
        if(file.isFile()) {
            chooseFile(file);
        }
    }


    @Override
    public void chooseFile(File file) {
        if((shower != null) && (!shower.isDone())) {
            shower.cancel(true);
        }
        shower = new TextShower();
        String content = fileProcessor.loadFileInCache(file);

        shower.showTextInTextArea(content);
        log.info("start to display");

//        waitDialog.showDialogAndLockTextArea(fileContentArea);
    }

    private class TextShower extends SwingWorker<String, String> {
        private String content;

        private void showTextInTextArea(String content) {
            this.content = content;
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
            log.info("Done");
//            waitDialog.close();
        }
    }
}
