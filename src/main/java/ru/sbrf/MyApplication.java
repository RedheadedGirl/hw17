package ru.sbrf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyApplication {
    public static void main(String[] args) {
        var factory = SpringApplication.run(MyApplication.class, args);
        RunExample example = factory.getBean(RunExample.class);
        example.run();
    }
}