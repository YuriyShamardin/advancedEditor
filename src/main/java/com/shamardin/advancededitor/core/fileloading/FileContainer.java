package com.shamardin.advancededitor.core.fileloading;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.Weigher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;

@Component
class FileContainer {
    private LoadingCache<File, String> filesContent;

    @Autowired
    private FileProcessor fileProcessor;

    @PostConstruct
    public void init() {
        filesContent = CacheBuilder.newBuilder()
                //in bytes
                .maximumWeight(1000000)
                .weigher((Weigher<File, String>) (k, content) -> content.getBytes().length)
                .build(new CacheLoader<File, String>() {
                    @Override
                    public String load(File key) throws Exception {
                        return fileProcessor.readFile(key);
                    }
                });
    }

    String get(File file) {
        return filesContent.getUnchecked(file);
    }

    boolean containsFile(File file) {
        return filesContent.asMap().containsKey(file);
    }
}
