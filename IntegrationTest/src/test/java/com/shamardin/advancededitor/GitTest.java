package com.shamardin.advancededitor;

import com.shamardin.advancededitor.core.git.FileStatus;
import com.shamardin.advancededitor.core.git.GitProcessor;
import com.shamardin.advancededitor.core.git.VcsProcessor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.Test;

import java.io.File;

@Slf4j
public class GitTest {


    @Test
    public void tstGit() {
        try (Git git = Git.init()
                .setDirectory(new File("E:\\Projects\\jet\\advancedEditor\\IntegrationTest\\src\\test\\resources"))
                .call()) {

            Status status = git.status().call();
            git.add().addFilepattern("1.txt").call();
            Status statusAfter = git.status().call();
            log.info("was add {}", statusAfter.getAdded());

//            git.reset().addPath("1.txt").call();
//            statusAfter = git.status().call();
//            log.info("after delete {}", statusAfter.getUntracked());


//            Iterable<RevCommit> iterable = git.log().call();
//            for (RevCommit commit : iterable) {
//                System.out.println(commit.getFullMessage());
//            }

        } catch (GitAPIException e1) {
            e1.printStackTrace();
        }
    }



}
