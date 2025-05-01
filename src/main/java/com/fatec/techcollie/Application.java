package com.fatec.techcollie;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplicationBuilder sb = new SpringApplicationBuilder(Application.class);
        sb.properties("spring.profiles.active=dev");
        sb.properties("server.port=8080");

        sb.run(args);
    }

}
