package com.shamardin.advancededitor.view;

import com.shamardin.advancededitor.Editor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class LoadingFileWaitDialog {
    @Autowired
    private Editor editor;

    private JTextArea textArea;

    private JDialog dialog = new JDialog(editor, "Opening file", Dialog.ModalityType.TOOLKIT_MODAL);

    public void show() {
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(progressBar, BorderLayout.CENTER);
        panel.add(new JLabel("File opening ......."), BorderLayout.PAGE_START);
        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(editor);
        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        dialog.setVisible(true);
    }

    public void showDialogAndBlockTextArea(JTextArea textArea) {
        this.textArea = textArea;
        textArea.setEditable(false);
        show();
    }

    public void close() {
        if(textArea != null) {
            textArea.setEditable(true);
        }
        dialog.dispose();
    }
}
