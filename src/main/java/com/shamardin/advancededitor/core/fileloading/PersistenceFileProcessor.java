package com.shamardin.advancededitor.core.fileloading;

import com.google.common.annotations.VisibleForTesting;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.Adler32;
import java.util.zip.Checksum;

import static lombok.AccessLevel.PACKAGE;

@Slf4j
@Component
public class PersistenceFileProcessor implements FileProcessor {

    @VisibleForTesting
    @Getter(PACKAGE)
//    private Map<File, Long> files = new HashMap<>();

    private Map<File, byte[]> filesContent = new HashMap<>();

    @Override
    public byte[] load(File file) {
        byte[] cachedContent = filesContent.computeIfAbsent(file, this::readFile);

//        Long fileHash = HashCounter.getFilesHash(cachedContent);
//        log.info("files hash is {}", fileHash);
//        files.put(file, fileHash);
        return cachedContent;
    }

    @VisibleForTesting
    byte[] readFile(File file) {
        final int length = (int) file.length();
        byte[] buffer = new byte[length];

        try (final FileInputStream fis = (new FileInputStream(file))) {
            final int readedData = fis.read(buffer);
            log.info("read {} bytes from file {}", readedData, file.getName());
        } catch (FileNotFoundException e) {
            buffer = "Can not open the file! For details look at log file".getBytes();
            log.error(e.getMessage(), e);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return buffer;
    }

    private char getHex(int i) {
        if(i < 10) {
            return (char) (i + '0');
        } else {
            return (char) (i - 10 + 'A');
        }
    }

    private static class HashCounter {
        private static Checksum hashCalculator = new Adler32();

        private static Long getFilesHash(byte[] content) {
            hashCalculator.update(content, 0, content.length);
            return hashCalculator.getValue();
        }
    }
}
