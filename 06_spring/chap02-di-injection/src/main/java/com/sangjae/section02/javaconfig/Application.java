package com.sangjae.section02.javaconfig;

import com.sangjae.common.MemberDTO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {

        ApplicationContext applicationContext
                = new AnnotationConfigApplicationContext(ContextConfigurationImpl.class);


        MemberDTO member = applicationContext.getBean("memberGenerator",MemberDTO.class);
        System.out.println(member.getPersonalAccount().getBalance());

    }

}
