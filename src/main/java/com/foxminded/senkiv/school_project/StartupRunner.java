package com.foxminded.senkiv.school_project;

import com.foxminded.senkiv.school_project.cli.CliHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements CommandLineRunner {
    @Autowired
    CliHandler cliHandler;

    @Override
    public void run(String... args) throws Exception {
        cliHandler.cliApplication();
    }
}
