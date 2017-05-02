package com.shamardin.advancededitor.core.fileloading;

import com.shamardin.advancededitor.controller.VCSController;
import com.shamardin.advancededitor.core.git.FileStatus;
import com.shamardin.advancededitor.core.git.GitProcessor;
import com.shamardin.advancededitor.core.git.VcsProcessor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;

@Slf4j
public class PersistenceFileProcessorTest {

    private String fileName = "E:\\Projects\\jet\\advancedEditor\\src\\test\\resources\\testContent.txt";
    private File file = new File(fileName);

    /*@Test
    public void fileShouldBeReadCorrectly() {
        //given:
        PersistenceFileProcessor fileProcessor = new PersistenceFileProcessor();
        String expectedContent = "123\r\n456\r\n789";

        //when:
        String loadedData = fileProcessor.readFileFromDisk(file);

        //then:
        assertThat(loadedData, is(expectedContent));
    }*/


  /*  @Test
    @Ignore
    public void fileShouldBeReadOneTime() {
        //given:
        PersistenceFileProcessor fileProcessor = spy(PersistenceFileProcessor.class);
        FileContainer container = new FileContainer();
        fileProcessor.setFileContainer(container);

        //when:
        for (int i = 0; i < 10; i++) {
            fileProcessor.trackFile(file);
        }

        //then:
        verify(fileProcessor, times(1)).readFileFromDisk(file);
    }*/


}