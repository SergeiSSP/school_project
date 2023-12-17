package com.foxminded.senkiv.school_project;

import com.foxminded.senkiv.school_project.cli.CliHandler;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements CommandLineRunner {
	private final CliHandler cliHandler;
    public StartupRunner(CliHandler cliHandler){
		this.cliHandler = cliHandler;
	}

    @Override
    public void run(String... args) throws Exception {
        cliHandler.cliApplication();
    }
}
