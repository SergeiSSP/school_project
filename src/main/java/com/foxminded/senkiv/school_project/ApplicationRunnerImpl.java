package com.foxminded.senkiv.school_project;

import com.foxminded.senkiv.school_project.seeder.DBStarter;
import org.flywaydb.core.Flyway;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationRunnerImpl implements ApplicationRunner {
	private final Flyway flyway;
	private final DBStarter dbStarter;

    public ApplicationRunnerImpl(Flyway flyway, DBStarter dbStarter) {
        this.flyway = flyway;
        this.dbStarter = dbStarter;
    }



    @Override
	public void run(ApplicationArguments args) {
		flyway.baseline();
		dbStarter.startUp();
	}
}
