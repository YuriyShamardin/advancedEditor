package com.shamardin.advancededitor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.swing.*;

@Configuration
@ComponentScan
public class Config {

    @Bean
    public Editor editor() {
        return new Editor();
    }

    @Bean
    public JSplitPane jSplitPane() {
        return new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    }

}
