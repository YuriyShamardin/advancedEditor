package com.shamardin.advancededitor.core.git;

import com.google.common.annotations.VisibleForTesting;
import com.shamardin.advancededitor.core.PathUtil;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.shamardin.advancededitor.core.PathUtil.getGitFriendlyPath;
import static com.shamardin.advancededitor.core.git.FileStatus.*;
import static org.apache.commons.lang3.ArrayUtils.INDEX_NOT_FOUND;
import static org.apache.commons.lang3.ArrayUtils.indexOf;
import static org.apache.commons.lang3.exception.ExceptionUtils.getMessage;

@Slf4j
@Component
public class GitProcessor implements VcsProcessor {
    private Git git;

    @Override
    @SuppressWarnings("null")
    public synchronized boolean openRepository(File file) {
        File[] files = file.listFiles();
        int gitDirectory = indexOf(files, new File(file.getPath() + File.separator + ".git"));
        if(gitDirectory == INDEX_NOT_FOUND) {
            return false;
        }

        try(Repository repository = new FileRepositoryBuilder()
                .setGitDir(files[gitDirectory])
                .build()) {
            log.info("found repository with status {}", repository.getRepositoryState());
            git = Git.wrap(repository);
            PathUtil.setRoot(file.getPath());
            return true;
        } catch(IOException e) {
            log.error(getMessage(e));
        }
        return false;
    }

    @Override
    public synchronized boolean createRepository(File file) {
        try(Git openGit = Git.init().setDirectory(file).call()) {
            this.git = openGit;
            PathUtil.setRoot(file.getPath());
            return true;
        } catch(GitAPIException e) {
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
        } catch(GitAPIException e) {
            log.error(getMessage(e));
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
        } catch(GitAPIException e) {
            log.error(getMessage(e));
        }
    }

    @Override
    public synchronized void revertFile(File file) {
        try {
            String rightPath = getGitFriendlyPath(file.getPath());
            git.checkout().setStartPoint("HEAD").addPath(rightPath).call();
        } catch(GitAPIException e) {
            log.error(getMessage(e));
        }
    }

    @Override
    public synchronized List<String> getAllRemovedFiles() {
        Status status;
        try {
            status = git.status().call();
            return newArrayList(status.getRemoved());
        } catch(GitAPIException e) {
            log.error(getMessage(e));
        }
        return new ArrayList<>();
    }

    @Override
    public synchronized List<String> getAllUntrackedFiles() {
        Status status;
        try {
            status = git.status().call();
            return newArrayList(status.getUntracked());
        } catch(GitAPIException e) {
            log.error(getMessage(e));
        }
        return new ArrayList<>();
    }


    @Override
    public synchronized FileStatus computeFileStatus(File file) {
        String rightPath = getGitFriendlyPath(file.getPath());
        Status fileStatus = getFileStatus(rightPath);
        if(fileStatus == null) {
            return UNKNOWN;
        }
        if(fileStatus.getModified().contains(rightPath)) {
            return MODIFIED;
        } else if(fileStatus.getRemoved().contains(rightPath)) {
            return REMOVED;
        } else if(fileStatus.getUntracked().contains(rightPath)) {
            return UNTRACKED;
        } else if(fileStatus.getAdded().contains(rightPath)) {
            return ADDED;
        } else if(fileStatus.getChanged().contains(rightPath)) {
            return GET_CHANGES;
        } else if(fileStatus.getMissing().contains(rightPath)) {
            return MISSING;
        } else if(fileStatus.isClean()) {
            return VERSIONED;
        }
        return UNKNOWN;
    }

    @VisibleForTesting
    synchronized Status getFileStatus(String filePath) {
        try {
            String rightPath = getGitFriendlyPath(filePath);
            return git.status().addPath(rightPath).call();
        } catch(GitAPIException e) {
            log.error(getMessage(e));
        }
        return null;
    }

    // TODO: 03-May-17 for future
    private synchronized void commitAllChanges(String message) {
        try {
            git.commit().setMessage(message).call();
        } catch(GitAPIException e) {
            log.error(getMessage(e));
        }
    }
}
