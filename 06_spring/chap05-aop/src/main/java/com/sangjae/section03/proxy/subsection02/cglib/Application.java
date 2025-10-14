package com.sangjae.section03.proxy.subsection02.cglib;

import com.sangjae.section03.proxy.common.SangjaeStudent;
import com.sangjae.section03.proxy.common.Student;
import org.springframework.cglib.proxy.Enhancer;


public class Application {
    public static void main(String[] args) {
        // 1. CGLib Proxy 방식
        // 바이트코드를 조작하며 Target Object 는 interface , class 모두가능
        SangjaeStudent student = new SangjaeStudent();
        Handler handler = new Handler(student);


        SangjaeStudent proxy = (SangjaeStudent) Enhancer.create(
                SangjaeStudent.class,handler
        ) ;


        proxy.study(16);
    }
}
