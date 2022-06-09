package io.github.aliothliu.gabbro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableGabbro
public class JSendTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(JSendTestApplication.class, args);
    }
}
