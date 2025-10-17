package com.ohgiraffers.exceptionhandler;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExceptionHandlerController {

    @GetMapping("/controller-null")
    public String nullPointerException() {
        String str = null;
        System.out.println(str.charAt(0));  // 의도적 Exception 발생 구문
        return "/";
    }

    @GetMapping("/controller-user")
    public String userException() {
        boolean check = true;
        if(check) throw new MemberRegistException("당신 같은 사람은 회원 불가!");
        return "/";
    }

    @ExceptionHandler(NullPointerException.class)
    public String nullPointerExceptionHandler(NullPointerException e) {
        System.out.println("지역 범위의 ExceptionHandler");
        System.out.println(e.getMessage());
        return "error/nullPointer";
    }

    @ExceptionHandler(MemberRegistException.class)
    public String userExceptionHandler(MemberRegistException e, Model model) {
        System.out.println("지역 범위의 ExceptionHandler");
        System.out.println(e.getMessage());
        model.addAttribute("exception", e);
        return "error/memberRegist";
    }
}
