package com.sangjae.secondservice.controller;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecondController {

    private final Environment environment;

    public SecondController(Environment environment){
        this.environment = environment;
    }

    // 스케일 아웃 된 상황에서 로드밸런싱 되는 모습을 확인하기위한 엔드포인트
    @GetMapping("/health")
    public String healthCheck(){
        return "Second Service is Ok. Port = " + environment.getProperty("local.server.port");
    }

    // API Gateway 에서 요청을 가공해서 전달하는모습 확인하기위한 엔드포인트
    @GetMapping("/message")
    public String message(@RequestHeader("first-request")String header){
        return "Second-request header : " + header;
    }

}
