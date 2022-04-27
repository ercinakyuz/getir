package com.getir;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
//@EnableMongoRepositories
public class ReadingIsGoodApi {
    public static void main(String[] args) {
        SpringApplication.run(ReadingIsGoodApi.class, args);
    }
}


