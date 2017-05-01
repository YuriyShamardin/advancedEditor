package com.shamardin.advancededitor.model;

import lombok.Value;

import java.io.File;

@Value
public class FileInfo {
    private File file;
    private String relativePath;
}
