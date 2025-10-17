package com.ohgiraffers.exceptionhandler;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/* 해당 어노테이션이 적용 된 클래스의 @ExceptionHandler는 전역적으로 기능한다.
* @ControllerAdvice 어노테이션은 스프링에서 예외 처리를 담당하는 어노테이션이다.
* @ControllerAdvice 어노테이션이 붙은 클래스는 전역 예외 처리를 담당하게 된다*/
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NullPointerException.class)
    public String nullPointerExceptionHandler(NullPointerException e) {
        System.out.println("전역 범위의 ExceptionHandler");
        System.out.println(e.getMessage());
        return "error/nullPointer";
    }

    @ExceptionHandler(MemberRegistException.class)
    public String userExceptionHandler(MemberRegistException e, Model model) {
        System.out.println("전역 범위의 ExceptionHandler");
        System.out.println(e.getMessage());
        model.addAttribute("exception", e);
        return "error/memberRegist";
    }

    // 모든 타입의 Exception 에 대응 하기 위해 상위 타입으로 선언 된 핸들러 작성
    @ExceptionHandler(Exception.class)
    public String defaultExceptionHandler(Exception e){
        return "error/default";
    }
}
