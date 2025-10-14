package com.sangjae.section03.proxy.subsection01.dynamic;

import com.sangjae.section03.proxy.common.SangjaeStudent;
import com.sangjae.section03.proxy.common.Student;

import java.lang.reflect.Proxy;

public class Application {
    public static void main(String[] args) {
        // 1. JDK Dynamic Proxy 방식
        // 리플랙션을 이용하여 Target Object는  interface만 가능
        Student student = new SangjaeStudent();
        Handler handler = new Handler(student);

        Student proxy = (Student) Proxy.newProxyInstance(
                Student.class.getClassLoader(), new Class[]{Student.class}, handler
        );
        proxy.study(16);
    }
}
