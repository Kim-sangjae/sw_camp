package com.sangjae.section01.xmlconfig;

import com.sangjae.common.MemberDTO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class Application {
    public static void main(String[] args) {

        ApplicationContext applicationContext = new GenericXmlApplicationContext(
                "section01/xmlconfig/spring-context.xml");

        MemberDTO member = (MemberDTO) applicationContext.getBean("member");
        member = applicationContext.getBean(MemberDTO.class);
        member = applicationContext.getBean("member", MemberDTO.class);

        System.out.println(member);
    }
}
