package com.example.hotelmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
        (scanBasePackages = {
                "com.example.hotelmanager",
        })
public class HotelManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelManagerApplication.class, args);
    }

}
