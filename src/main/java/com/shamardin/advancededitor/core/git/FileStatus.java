package com.shamardin.advancededitor.core.git;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.*;

import static java.awt.Color.*;

@AllArgsConstructor
public enum FileStatus {
    UNTRACKED(PINK), MODIFIED(CYAN), REMOVED(GRAY), VERSIONED(WHITE), UNKNOWN(YELLOW), GET_CHANGES(BLUE), MISSING(LIGHT_GRAY), ADDED(GREEN);

    @Getter
    private final Color color;

}
