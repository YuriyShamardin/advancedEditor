package com.shamardin.advancededitor.core.git;

import com.shamardin.advancededitor.Config;
import com.shamardin.advancededitor.core.PathUtil;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Status;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

@Slf4j
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
    public void repositoryShouldSetRootInPathUtils() {
        //when
        processor.createRepository(dirWithRepo);

        //then
        assertThat(PathUtil.getRoot(), is(dirWithRepo.getPath()));
    }

    @Test
    public void repositoryShouldOpenExistRepo() {
        //when
        boolean success = processor.openRepository(dirWithRepo);

        //then
        assertThat(success, is(true));
    }

    @Test
    public void repositoryShouldReturnFalseForNonExistRepo() {
        //when
        boolean success = processor.openRepository(dirWithoutRepo);
        //then
        assertThat(success, is(false));
    }

    @Test
    public void repositoryShouldAddFileInRepo() {
        //given
        processor.openRepository(dirWithRepo);
        String filePath = "inner/2.txt";

        //when
        processor.addFile(new File(filePath));

        //then
        Status status = processor.getFileStatus(filePath);
        Set<String> added = status.getAdded();

        assertThat(added, contains(filePath));
    }
}