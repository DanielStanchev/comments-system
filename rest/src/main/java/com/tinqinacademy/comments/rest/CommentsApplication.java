package com.tinqinacademy.comments.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Slf4j
@ComponentScan(basePackages = "com.tinqinacademy.comments")
@EntityScan(basePackages = "com.tinqinacademy.comments.persistence.entity")
@EnableJpaRepositories(basePackages = "com.tinqinacademy.comments.persistence.repository")
@SpringBootApplication
public class CommentsApplication {


    public static void main(String[] args) {
        SpringApplication.run(CommentsApplication.class, args);
    }
}
