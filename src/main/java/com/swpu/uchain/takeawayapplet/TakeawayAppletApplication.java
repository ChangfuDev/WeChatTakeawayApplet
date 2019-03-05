package com.swpu.uchain.takeawayapplet;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.swpu.uchain.takeawayapplet.dao")
public class TakeawayAppletApplication {

    public static void main(String[] args) {
        SpringApplication.run(TakeawayAppletApplication.class, args);
    }

}
