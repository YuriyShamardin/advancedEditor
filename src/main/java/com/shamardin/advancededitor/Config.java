package com.shamardin.advancededitor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.swing.*;

import static javax.swing.JSplitPane.HORIZONTAL_SPLIT;

@Configuration
@ComponentScan
public class Config {

    @Bean
    public Editor editor() {
        return new Editor();
    }

    @Bean
    public JSplitPane jSplitPane() {
        return new JSplitPane(HORIZONTAL_SPLIT);
    }

}
