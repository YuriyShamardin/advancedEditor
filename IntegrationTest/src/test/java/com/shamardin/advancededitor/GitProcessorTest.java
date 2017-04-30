package com.shamardin.advancededitor;

import com.shamardin.advancededitor.core.fileloading.GitProcessor;
import org.eclipse.jgit.lib.Repository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Config.class})
public class GitProcessorTest {
    @Autowired
    private GitProcessor processor;

    private File dirWithRepo = new File("E:\\Projects\\jet\\advancedEditor\\IntegrationTest\\src\\test\\resources");
    private File dirWithoutRepo = new File("E:\\Projects\\jet\\advancedEditor\\IntegrationTest\\src\\test\\resources\\inner");


    @Test
    public void repositoryShouldOpenExistRepo() {
        //when
        Repository repository = processor.openRepository(dirWithRepo);

        //then
        assertThat(repository, is(notNullValue()));
    }

    @Test
    public void repositoryShouldReturnNullForNonExistRepo() {
        //when
        Repository repository = processor.openRepository(dirWithoutRepo);
        //then
        assertThat(repository, is(nullValue()));
    }


}