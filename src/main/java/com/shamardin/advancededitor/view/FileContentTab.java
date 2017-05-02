package com.shamardin.advancededitor.view;

import com.shamardin.advancededitor.listener.SelectTabListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

@Slf4j
@Component
public class FileContentTab extends JTabbedPane {
    @Autowired
    private SelectTabListener selectTabListener;

    @PostConstruct
    public void init() {
        setPreferredSize(new Dimension(200, 400));
        setMinimumSize(new Dimension(100, 400));
        addChangeListener(selectTabListener);
    }
}
