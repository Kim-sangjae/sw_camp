package com.ohgiraffers.exceptionhandler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OtherController {
    @GetMapping("/other-controller-null")
    public String nullPointerException() {
        String str = null;
        System.out.println(str.charAt(0));  // 의도적 Exception 발생 구문
        return "/";
    }

    @GetMapping("/other-controller-user")
    public String userException() {
        boolean check = true;
        if (check) throw new MemberRegistException("당신 같은 사람은 회원 불가!");
        return "/";
    }


    @GetMapping("/other-controller-array")
    public String arrayException() {
        double[] arr = new double[0];
        System.out.println(arr[0]);
        return "/";
    }
}
