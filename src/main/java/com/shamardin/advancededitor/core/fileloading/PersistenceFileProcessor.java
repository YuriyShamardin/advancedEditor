package com.shamardin.advancededitor.core.fileloading;

import com.google.common.annotations.VisibleForTesting;
import lombok.AccessLevel;
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
//@Component
public class PersistenceFileProcessor implements FileProcessor {

    @VisibleForTesting
    @Getter(value = PACKAGE)
    private Map<String, Long> files = new HashMap<>();

    @Override
    public byte[] load(String fileName) {
        byte[] content = readFile(fileName);
        Long fileHash = HashCounter.getFilesHash(content);
        log.info("files hash is {}", fileHash);
        files.put(fileName, fileHash);
        return content;
    }

    @VisibleForTesting
    byte[] readFile(String fileName) {
        File file = new File(fileName);
        final int length = (int) file.length();
        final byte[] array = new byte[length];

        try (final FileInputStream fis = (new FileInputStream(file))) {
            final int readedData = fis.read(array);

            /*final char[] cbuf = new char[length * 2];
            int cbufIndex = 0;
            for (int i = 0; i < read; i++) {
                int tmp_int = array[i];
                cbuf[cbufIndex++] = getHex((tmp_int >> 4) & 0xf);
                cbuf[cbufIndex++] = getHex((tmp_int) & 0xf);
            }
            String hexData = new String(cbuf);*/
            log.info("read {} bytes from file {}", readedData, fileName);
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return array;
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
