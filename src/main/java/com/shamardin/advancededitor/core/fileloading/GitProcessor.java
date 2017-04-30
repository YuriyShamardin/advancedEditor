package com.shamardin.advancededitor.core.fileloading;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

import static org.apache.commons.lang3.ArrayUtils.INDEX_NOT_FOUND;
import static org.apache.commons.lang3.ArrayUtils.indexOf;

@Slf4j
@Component
public class GitProcessor implements VcsProcessor {

    @Override
    @SuppressWarnings("null")
    public Repository openRepository(File file) {
        File[] files = file.listFiles();
        int gitDirectory = indexOf(files, new File(file.getPath() + File.separator + ".git"));
        if(gitDirectory == INDEX_NOT_FOUND) {
            return null;
        }

        try (Repository repository = new FileRepositoryBuilder()
                .setGitDir(files[gitDirectory])
                .build()) {
            log.info("found repository with status {}", repository.getRepositoryState());
            return repository;
        } catch (IOException e) {
            log.error("{}", e);
        }
        return null;
    }
}
