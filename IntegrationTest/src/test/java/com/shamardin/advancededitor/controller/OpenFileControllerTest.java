package com.shamardin.advancededitor.controller;

import com.shamardin.advancededitor.Config;
import com.shamardin.advancededitor.Editor;
import com.shamardin.advancededitor.view.FileContentArea;
import lombok.SneakyThrows;
import org.fest.swing.core.ComponentFinder;
import org.fest.swing.core.Robot;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import javax.swing.*;
import java.awt.*;

import static org.fest.swing.core.BasicRobot.robotWithCurrentAwtHierarchy;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Config.class})
public class OpenFileControllerTest {
    @Autowired
    private Editor editor;
    @Before
    public void startApp(){
        editor.start();
    }



   /* @Test
    @SneakyThrows
    public void robotTest() {
        //given
        Robot robot = robotWithCurrentAwtHierarchy();
        ComponentFinder finder = robot.finder();

        //when
        Component menu = finder.find(comp -> (comp instanceof JMenu) && ((JMenu) comp).getText().equals("Menu"));
        robot.click(menu);
        Component exit = finder.find(comp -> (comp instanceof JMenuItem) && ((JMenuItem) comp).getText().equals("Exit"));
        robot.click(exit);
        //then
    }
*/
    @Test
    @SneakyThrows
    public void controllerShouldFullTextAreaFromFile() {
        //given
        Robot robot = robotWithCurrentAwtHierarchy();
        ComponentFinder finder = robot.finder();
        String expectedString = "123\r\n456\r\n789";

        //when
        Component menu = finder.find(comp -> (comp instanceof JMenu) && ((JMenu) comp).getText().equals("Menu"));
        robot.click(menu);
        Component openFile = finder.find(comp -> (comp instanceof JMenuItem) && ((JMenuItem) comp).getText().equals("Open file"));
        robot.click(openFile);
        FileContentArea textArea = (FileContentArea) finder.find(comp -> (comp instanceof FileContentArea));

        //then
        assertThat(textArea.getText(),is(expectedString));
    }
}