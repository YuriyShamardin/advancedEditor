package com.shamardin.advancededitor.core.git;

import com.google.common.annotations.VisibleForTesting;
import com.shamardin.advancededitor.PathUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static com.shamardin.advancededitor.PathUtil.getGitFriendlyPath;
import static org.apache.commons.lang3.ArrayUtils.INDEX_NOT_FOUND;
import static org.apache.commons.lang3.ArrayUtils.indexOf;

@Slf4j
@Component
public class GitProcessor implements VcsProcessor {
    private Git git;
    private Status status;

    @Override
    @SuppressWarnings("null")
    public synchronized boolean openRepository(File file) {
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
            PathUtil.setRoot(file.getPath());
            return true;
        } catch (IOException e) {
            log.error("{}", e);
        }
        return false;
    }

    @Override
    public synchronized boolean createRepository(File file) {
        try (Git git = Git.init().setDirectory(file).call()) {
            this.git = git;
            PathUtil.setRoot(file.getPath());
            return true;
        } catch (GitAPIException e) {
            log.error("Unknown error during creation repository {}", e);
        }
        return false;
    }

    @Override
    public synchronized void addFile(File file) {
        try {
            String rightPath = getGitFriendlyPath(file.getPath());

            DirCache call = git.add().addFilepattern(rightPath).call();

            if(call.getEntry(rightPath) != null) {
                log.info("success added file {} to git", file);
            } else {
                log.info("Can not add file {} to git", file);
            }
        } catch (GitAPIException e) {
            log.error(ExceptionUtils.getMessage(e));
        }
    }

    @Override
    public synchronized void removeFile(File file) {
        try {
            String rightPath = getGitFriendlyPath(file.getPath());
            git.rm().addFilepattern(rightPath).call();

            Status call = git.status().call();
            if(call.getRemoved().contains(rightPath)) {
                log.info("success remove file {} from git", file);
            } else {
                log.info("Can not remove file {} from git", file);
            }
        } catch (GitAPIException e) {
            log.error(ExceptionUtils.getMessage(e));
        }
    }

    @Override
    public synchronized void revertFile(File file) {
        Ref call;
        try {
            String rightPath = getGitFriendlyPath(file.getPath());
            call = git.checkout().setStartPoint("HEAD").addPath(rightPath).call();
        } catch (GitAPIException e) {
            log.error(ExceptionUtils.getMessage(e));
        }
    }

    @Override
    public synchronized List<String> getAllRemovedFiles() {
        Status status;
        try {
            status = git.status().call();
            return newArrayList(status.getRemoved());
        } catch (GitAPIException e) {
            log.error(ExceptionUtils.getMessage(e));
        }
        return new ArrayList<>();
    }

    @Override
    public synchronized List<String> getAllUntrackedFiles() {
        Status status;
        try {
            status = git.status().call();
            return newArrayList(status.getUntracked());
        } catch (GitAPIException e) {
            log.error(ExceptionUtils.getMessage(e));
        }
        return new ArrayList<>();
    }


    @Override
    public FileStatus computeFileStatus(File file) {
        String rightPath = file.getPath().replace("\\", "/");
        Status fileStatus = getFileStatus(rightPath);
        if(fileStatus.getModified().contains(rightPath)) {
            return FileStatus.MODIFIED;
        } else if(fileStatus.getRemoved().contains(rightPath)) {
            return FileStatus.REMOVED;
        } else if(fileStatus.getUntracked().contains(rightPath)) {
            return FileStatus.UNTRACKED;
        } else if(fileStatus.getAdded().contains(rightPath)) {
            return FileStatus.ADDED;
        } else if(fileStatus.getChanged().contains(rightPath)) {
            return FileStatus.GET_CHANGES;
        } else if(fileStatus.getMissing().contains(rightPath)) {
            return FileStatus.MISSING;
        } else if(fileStatus.isClean()) {
            return FileStatus.VERSIONED;
        }
        return FileStatus.UNKNOWN;
    }

    @SneakyThrows
    private synchronized void updateStatus() {
        status = git.status().call();
    }

    @SneakyThrows
    @VisibleForTesting
    synchronized Status getFileStatus(String filePath) {
        return git.status().addPath(filePath).call();
    }

    @SneakyThrows
    private synchronized Set<String> getUntrackedFiles() {
        updateStatus();
        return status.getUntracked();
    }

    @SneakyThrows
    synchronized void commitAllChanges(String message) {
        git.commit().setMessage(message).call();
    }
}
