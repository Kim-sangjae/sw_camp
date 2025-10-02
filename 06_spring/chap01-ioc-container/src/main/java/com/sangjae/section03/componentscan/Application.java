package com.sangjae.section03.componentscan;

import com.sangjae.common.MemberDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Application {
    public static void main(String[] args) {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ConfigurationContext.class);

        // 해당 Ioc Container 에 등록된 bean 모두출력
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            System.out.println("beanName : " +beanName);
        }

        MemberDAO memberDAO = applicationContext.getBean("memberDAO", MemberDAO.class);
        System.out.println(memberDAO.selectMember(1));

    }
}
