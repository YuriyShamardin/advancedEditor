package com.shamardin.advancededitor.controller;

import com.shamardin.advancededitor.core.fileloading.FileProcessor;
import com.shamardin.advancededitor.view.LoadingFileWaitDialog;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.io.*;
import java.util.List;

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
    @SneakyThrows
    public void chooseFile(File file) {
        if((shower != null) && (!shower.isDone())) {
            shower.cancel(true);
        }
        shower = new TextShower();
        byte[] content = fileProcessor.load(file);
        shower.showTextInTextArea(content);
        waitDialog.showDialogAndBlockTextArea(fileContentArea);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(!e.getValueIsAdjusting()) {
            File file = (File) ((JList) e.getSource()).getSelectedValue();
            log.info("choose file {}", file);
            chooseFile(file);
        }
    }

    private class TextShower extends SwingWorker<String, String> {
        private byte[] content;

        private void showTextInTextArea(byte[] content) {
            this.content = content;
            fileContentArea.setText("");
            execute();
        }

        @Override
        @SneakyThrows
        protected String doInBackground() {

            try (BufferedReader stream = new BufferedReader(
                    new InputStreamReader(new BufferedInputStream((new ByteArrayInputStream(content)))))) {
                String line;
                while ((line = stream.readLine()) != null) {
//                    Thread.sleep(2000);
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
            }
            fileContentArea.append(buffer.toString());
        }

        @Override
        protected void done() {
            waitDialog.close();
        }
    }
}
