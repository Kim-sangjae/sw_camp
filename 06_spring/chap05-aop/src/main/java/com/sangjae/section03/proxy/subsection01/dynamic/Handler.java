package com.sangjae.section03.proxy.subsection01.dynamic;

import com.sangjae.section03.proxy.common.Student;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class Handler implements InvocationHandler {

    // 타겟 오브젝트
    private final Student student;

    public Handler(Student student) {
        this.student = student;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("---- 공부 하기 싫어요 ----");
        System.out.println("호출 대상 메소드 : " + method);
        for (Object arg : args) System.out.println("전달 인자 : " + arg);

        // 타겟 오브젝트의 메서드 호출
        method.invoke(student, args);

        System.out.println("--- 공부 끝 잠을 잡니다 ---");

        return proxy;
    }


}
