package com.shamardin.advancededitor.core.fileloading;

import com.google.common.annotations.VisibleForTesting;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static lombok.AccessLevel.PACKAGE;

@Slf4j
@Component
public class PersistenceFileProcessor implements FileProcessor {

    @VisibleForTesting
    @Getter(PACKAGE)
    private Map<File, byte[]> filesContent = new HashMap<>();

    @Override
    public byte[] load(File file) {
        return filesContent.computeIfAbsent(file, this::readFile);
    }

    @VisibleForTesting
    byte[] readFile(File file) {
        final int length = (int) file.length();
        byte[] buffer = new byte[length];

        try (final FileInputStream fis = (new FileInputStream(file))) {
            final int readData = fis.read(buffer);
            log.info("read {} bytes from file {}", readData, file.getName());
        } catch (IOException e) {
            buffer = "Can not open the file! For details look at log file".getBytes();
            log.error(e.getMessage(), e);
        }
        return buffer;
    }

    @Override
    public boolean isLoaded(File file) {
        return filesContent.containsKey(file);
    }
}
