package com.shamardin.advancededitor.core.git;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import static org.apache.commons.lang3.ArrayUtils.INDEX_NOT_FOUND;
import static org.apache.commons.lang3.ArrayUtils.indexOf;

@Slf4j
@Component
public class GitProcessor implements VcsProcessor {

    private Git git;

    private Status status;

    @Override
    @SuppressWarnings("null")
    public boolean openRepository(File file) {
        File[] files = file.listFiles();
        int gitDirectory = indexOf(files, new File(file.getPath() + File.separator + ".git"));
        if(gitDirectory == INDEX_NOT_FOUND) {
            return false;
        }

        try (Repository repository = new FileRepositoryBuilder()
                .setGitDir(files[gitDirectory])
                .build()) {
            log.info("found repository with status {}", repository.getRepositoryState());
            git = Git.wrap(repository);
            return true;
        } catch (IOException e) {
            log.error("{}", e);
        }
        return false;
    }

    @Override
    public boolean createRepository(File file) {
        try (Git git = Git.init().setDirectory(file).call()) {
            this.git = git;
            return true;
        } catch (GitAPIException e) {
            log.error("Unknown error during creation repository {}", e);
        }
        return false;
    }

    @Override
    public void addFile(File file) {
        try {
            String rightPath = file.getPath().replace("\\", "/");

            DirCache call = git.add().addFilepattern(rightPath).call();

            if(call.getEntry(rightPath) != null) {
                log.info("success added file {} to git", file);
            } else {
                log.info("Can not add file {} to git", file);
            }
        } catch (GitAPIException e) {
            log.info(ExceptionUtils.getMessage(e));
        }
    }

    @Override
    public synchronized FileStatus computeFileStatus(File file) {
        String rightPath = file.getPath().replace("\\", "/");
        Status fileStatus = getFileStatus(rightPath);
        if(fileStatus.getModified().contains(rightPath)) {
            return FileStatus.MODIFIED;
        } else if(fileStatus.getRemoved().contains(rightPath)) {
            return FileStatus.REMOVED;
        } else if(fileStatus.getUntracked().contains(rightPath)) {
            return FileStatus.UNTRACKED;
        } else if(fileStatus.getAdded().contains(rightPath)) {
            return FileStatus.VERSIONED;
        }
        return FileStatus.UNKNOWN;
    }

    @SneakyThrows
    private void updateStatus() {
        status = git.status().call();
    }

    @SneakyThrows
    private Status getFileStatus(String filePath) {
        return git.status().addPath(filePath).call();
    }

    @SneakyThrows
    private Set<String> getUntrackedFiles() {
        updateStatus();
        return status.getUntracked();
    }
}
