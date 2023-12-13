package com.foxminded.senkiv.school_project;

import com.foxminded.senkiv.school_project.database.StudentsDAO;
import com.foxminded.senkiv.school_project.database.mapper.CourseRowMapper;
import com.foxminded.senkiv.school_project.database.mapper.GroupRowMapper;
import com.foxminded.senkiv.school_project.database.mapper.StudentRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class TestSpringBootSchoolAppApplication {

    @Bean
    @ServiceConnection
    PostgreSQLContainer<?> postgresContainer() {
        return new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));
    }

    public static void main(String[] args) {
        SpringApplication.from(SpringBootSchoolAppApplication::main).with(TestSpringBootSchoolAppApplication.class).run(args);
    }
}
