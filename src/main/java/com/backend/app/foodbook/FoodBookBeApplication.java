package com.backend.app.foodbook;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableEncryptableProperties
public class FoodBookBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoodBookBeApplication.class, args);
    }

}
