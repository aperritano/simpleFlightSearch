package com.united;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Main entry for the spring application
 */
@SpringBootApplication
public class SimpleFlightSearchApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SimpleFlightSearchApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SimpleFlightSearchApplication.class, args);
    }

}
