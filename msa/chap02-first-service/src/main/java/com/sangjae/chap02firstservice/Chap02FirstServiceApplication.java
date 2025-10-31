package com.sangjae.chap02firstservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient // 해당 어플리케이션을 서비스 발견 클라이언트로 설정
public class Chap02FirstServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(Chap02FirstServiceApplication.class, args);
    }

}
