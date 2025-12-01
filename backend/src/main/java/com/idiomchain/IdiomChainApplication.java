package com.idiomchain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.idiomchain.repository")
public class IdiomChainApplication {

    public static void main(String[] args) {
        SpringApplication.run(IdiomChainApplication.class, args);
    }
}