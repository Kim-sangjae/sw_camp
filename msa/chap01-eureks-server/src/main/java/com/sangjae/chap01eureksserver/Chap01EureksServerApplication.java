package com.sangjae.chap01eureksserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer // 해당 어플리케이션을 유레카 서버로 활성화
public class Chap01EureksServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Chap01EureksServerApplication.class, args);
    }

}
