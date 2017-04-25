package com.shamardin.advancededitor;

import com.shamardin.advancededitor.view.component.MainPanel;
import lombok.SneakyThrows;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;

import static org.hamcrest.Matchers.hasItemInArray;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class EditorTest {

    private Robot robot;

    @Test
    @SneakyThrows
    public void editorShouldContainMainPanelAndMenuBar() {
        //given
        Editor editor = new Editor();

        //when
        JMenuBar menuBar = editor.getJMenuBar();
        MainPanel mainPanel = editor.getMainPanel();

        //than
        assertThat(menuBar, is(notNullValue()));
        assertThat(mainPanel, is(notNullValue()));
    }

}