package com.shamardin.advancededitor.view;

import com.shamardin.advancededitor.Editor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

import static java.awt.BorderLayout.PAGE_START;
import static java.awt.Dialog.ModalityType.MODELESS;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

@Component
public class LoadingFileWaitDialog {
    @Autowired
    private Editor editor;

    private JTextArea textArea;

    private JDialog dialog;

    @PostConstruct
    public void init() {
        dialog = new JDialog(editor, "Opening file", MODELESS);
    }

    private synchronized void show() {
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(progressBar, BorderLayout.CENTER);
        panel.add(new JLabel("File opening ......."), PAGE_START);

        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(editor);
        dialog.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
    }

    public synchronized void showDialogAndLockTextArea(JTextArea textArea) {
        this.textArea = textArea;
        textArea.setEditable(false);
        show();
    }

    public synchronized  void close() {
        if(textArea != null) {
            textArea.setEditable(true);
        }
        dialog.dispose();
    }
}
