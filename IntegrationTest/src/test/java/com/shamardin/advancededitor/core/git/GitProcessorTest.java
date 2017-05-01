package com.shamardin.advancededitor.core.git;

import com.shamardin.advancededitor.Config;
import com.shamardin.advancededitor.core.git.GitProcessor;
import org.eclipse.jgit.api.Status;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Config.class})
public class GitProcessorTest {
    @Autowired
    private GitProcessor processor;

    private File dirWithRepo = new File("E:\\Projects\\jet\\advancedEditor\\IntegrationTest\\src\\test\\resources");
    private File dirWithoutRepo = new File("E:\\Projects\\jet\\advancedEditor\\IntegrationTest\\src\\test\\resources\\inner");

    @Test
    public void repositoryShouldCreateRepo() {
        //when
        boolean success = processor.createRepository(dirWithRepo);

        //then
        assertThat(success, is(true));
    }

    @Test
    public void repositoryShouldOpenExistRepo() {
        //when
        boolean success = processor.openRepository(dirWithRepo);

        //then
        assertThat(success, is(true));
    }

    @Test
    public void repositoryShouldReturnNullForNonExistRepo() {
        //when
        boolean success = processor.openRepository(dirWithoutRepo);
        //then
        assertThat(success, is(false));
    }

    @Test
    public void repositoryShouldAddFileInRepo() {
        //given
        boolean success = processor.openRepository(dirWithRepo);
        File file = new File("inner/2.txt");
        File file1 = new File("1.txt");

        //when
        processor.addFile(file);
        processor.addFile(file1);

        //then
        assertThat(success, is(true));
    }

   /* @Test
    public void repositoryShouldFindStatusOfFile() {
        //given
        boolean success = processor.openRepository(dirWithRepo);
        File file = new File("1.txt");
        File file1 = new File("inner/2.txt");
        File file2 = new File("3.txt");

//        processor.addFile(file);
//        processor.addFile(file1);

        //when
        Status fileStatus = processor.getFileStatus(file);
        Status fileStatus1 = processor.getFileStatus(file1);
        Status fileStatus2 = processor.getFileStatus(file2);

        //then
        Set<String> untrackedFolders = fileStatus.getUntrackedFolders();
        Set<String> untrackedFolders1 = fileStatus1.getUntrackedFolders();
        Set<String> untrackedFolders2 = fileStatus2.getUntrackedFolders();
        assertThat(success, is(true));
    }*/
}