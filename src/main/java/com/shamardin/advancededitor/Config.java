package com.shamardin.advancededitor;

import com.shamardin.advancededitor.view.FileContentArea;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.swing.*;

import static javax.swing.JSplitPane.HORIZONTAL_SPLIT;

@Configuration
@ComponentScan
public class Config {

    @Bean
    public Editor editor() {
        return new Editor();
    }

}
