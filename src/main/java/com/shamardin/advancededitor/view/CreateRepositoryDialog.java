package com.shamardin.advancededitor.view;

import javax.swing.*;

public class CreateRepositoryDialog extends JDialog {
    public static boolean isNeedToCreateRepository() {
        int answer = JOptionPane.showOptionDialog(null,
                "There is not git, \nwould you like to create it?",
                "Create git?",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new Object[]{"Yes", "No"},
                "No");
        return answer == 0;
    }
}
