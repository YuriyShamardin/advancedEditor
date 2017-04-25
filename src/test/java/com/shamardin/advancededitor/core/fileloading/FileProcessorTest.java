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

    @Test
    public void fileShouldBeRed() {
        //given:

        //when:
        byte[] loadedData = fileProcessor.readFile(file);

        //then:
        assertThat(loadedData, is(new byte[]{49, 50, 51, 13, 10, 52, 53, 54, 13, 10, 55, 56, 57}));
        log.info(new String(loadedData));
    }

    @Test
    public void afterLoadingFileBufferShouldContainRecord() {
        //given:
        long hash = 234684940;

        //when:
        fileProcessor.load(file);
        Map<File, Long> files = fileProcessor.getFiles();

        //then:
        assertThat(files, hasEntry(file, hash));
    }
}