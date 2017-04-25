package com.shamardin.advancededitor.view.component;


import com.google.common.base.Objects;
import org.springframework.stereotype.Component;

import javax.swing.*;

//@Component
public class MainMenu extends JMenuBar {


    public MainMenu() {

        JMenu mainTab = new JMenu("Menu");

        JMenuItem openFile = new JMenuItem("Open file");
        mainTab.add(openFile);

        JMenuItem exit = new JMenuItem("Exit");
        mainTab.add(exit);
        exit.addActionListener(e -> System.exit(0));

        add(mainTab);
    }

/*
    private void loadFile(String fileName, JTextPane textArea) {
        JPanel popup = new JPanel(new BorderLayout(5, 5));
        popup.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.gray),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        popup.setBackground(Color.white);
        add(popup);

        JProgressBar pb = new JProgressBar();
        popup.add(pb, BorderLayout.CENTER);
        pb.setBorderPainted(true);
        pb.setMinimum(0);
        pb.setMaximum(100);
        pb.setValue(0);
        pb.setStringPainted(true);
        pb.setIndeterminate(false);
        JButton cancel = new JButton("Cancel");
        popup.add(cancel, BorderLayout.EAST);
        popup.doLayout();
        Dimension size = popup.getPreferredSize();
        Dimension windowSize = new Dimension(800, 600);
        int popupWidth = 400;
        popup.setBounds((windowSize.width - popupWidth) / 2, (windowSize.height - size.height) / 2,
                popupWidth, size.height);
//        getLayeredPane().setLayer(popup, JLayeredPane.POPUP_LAYER);

        popup.setVisible(false);

        cancel.addActionListener(e -> loader.cancel());

        File f = new File(fileName);
        UICallback ui = (UICallback) Proxy.newProxyInstance(getClass().getClassLoader(),
                new Class[]{UICallback.class}, new EDTInvocationHandler(new UICallbackImpl(textArea, pb, popup)));
        if(!f.exists()) {
            ui.showError("File " + fileName + " doesn't exist!");
            return;
        }
        if(f.isDirectory()) {
            ui.showError("File " + fileName + " is a directory!");
            return;
        }

        loader = new CustomLoader(ui, fileName);
        loader.load();
    }*/
}
