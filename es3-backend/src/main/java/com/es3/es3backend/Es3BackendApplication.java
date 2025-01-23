package com.es3.es3backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Es3BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(Es3BackendApplication.class, args);
    }

}
