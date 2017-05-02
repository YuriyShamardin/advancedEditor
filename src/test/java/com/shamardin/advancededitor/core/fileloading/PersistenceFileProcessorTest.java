package com.shamardin.advancededitor.core.fileloading;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@Slf4j
public class PersistenceFileProcessorTest {

    private String fileName = "E:\\Projects\\jet\\advancedEditor\\src\\test\\resources\\testContent.txt";
    private File file = new File(fileName);
    private PersistenceFileProcessor fileProcessor = new PersistenceFileProcessor();
    private String exceptedErrorMessage = "Can not open the file! For details look at log file";

    @Test
    public void fileShouldBeReadCorrectly() {
        //given:
        String expectedContent = "123\r\n456\r\n789";

        //when:
        String loadedData = fileProcessor.readFileFromDisk(file);

        //then:
        assertThat(loadedData, is(expectedContent));
    }

    @Test
    public void fileShouldReturnExceptedErrorMessage() {
        //given:
        String exceptedErrorMessage = "Can not open the file! For details look at log file";

        //when:
        String loadedData = fileProcessor.readFileFromDisk(new File(""));

        //then:
        assertThat(loadedData, is(exceptedErrorMessage));
    }

    @Test
    public void fileShouldReturnExceptedErrorMessageForNullParam() {
        //given:
        File f = null;

        //when:
        String loadedData = fileProcessor.readFileFromDisk(f);

        //then:
        assertThat(loadedData, is(exceptedErrorMessage));
    }
}