package com.shamardin.advancededitor.core.fileloading;

import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@Slf4j
public class PersistenceFileProcessorTest {

    private String fileName = "E:\\Projects\\jet\\advancedEditor\\src\\test\\resources\\testContent.txt";
    private File file = new File(fileName);

    @Test
    public void fileShouldBeReadCorrectly() {
        //given:
        PersistenceFileProcessor fileProcessor = new PersistenceFileProcessor();
        String expectedContent = "123\r\n456\r\n789";

        //when:
        String loadedData = fileProcessor.readFile(file);

        //then:
        assertThat(loadedData, is(expectedContent));
    }


    @Test
    // TODO: 30-Apr-17 should replace to integration test
    @Ignore
    public void fileShouldBeReadOneTime() {
        //given:
        PersistenceFileProcessor fileProcessor = spy(PersistenceFileProcessor.class);
        FileContainer container = new FileContainer();
        fileProcessor.setFileContainer(container);

        //when:
        for (int i = 0; i < 10; i++) {
            fileProcessor.loadFileInCache(file);
        }

        //then:
        verify(fileProcessor, times(1)).readFile(file);
    }

}