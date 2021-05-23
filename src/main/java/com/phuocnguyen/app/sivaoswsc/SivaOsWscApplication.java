package com.phuocnguyen.app.sivaoswsc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SivaOsWscApplication {

    public static void main(String[] args) {
        SpringApplication.run(SivaOsWscApplication.class, args);
    }

}
