package com.foxminded.senkiv.school_project;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class SpringBootSchoolAppApplication {
    public static void main(String[] args){
        var spring = new SpringApplication();
        spring.setBannerMode(Banner.Mode.OFF);
        spring.run(SpringBootSchoolAppApplication.class, args);
    }

}
