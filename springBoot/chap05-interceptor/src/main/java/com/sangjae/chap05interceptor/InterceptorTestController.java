package com.sangjae.chap05interceptor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InterceptorTestController {

    @GetMapping("/stopwatch")
    public String stopwatch(){
        System.out.println("Handler Method 호출");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "result";
    }
}
