package com.shamardin.advancededitor.core.fileloading;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;

@Slf4j
public class FileProcessorTest {
    private PersistenceFileProcessor fileProcessor = new PersistenceFileProcessor();
    private String fileName = "E:\\Projects\\jet\\advancedEditor\\src\\test\\resources\\testContent.txt";
    private File file = new File(fileName);
    private byte[] content = {49, 50, 51, 13, 10, 52, 53, 54, 13, 10, 55, 56, 57};

    @Test
    public void fileShouldBeRed() {
        //given:

        //when:
        byte[] loadedData = fileProcessor.readFile(file);

        //then:
        assertThat(loadedData, is(content));
        log.info(new String(loadedData));
    }

    @Test
    public void afterLoadingFileBufferShouldContainRecord() {
        //given:

        //when:
        fileProcessor.load(file);
        Map<File, byte[]> files = fileProcessor.getFilesContent();

        //then:
        assertThat(files, hasEntry(file, content));
    }
}