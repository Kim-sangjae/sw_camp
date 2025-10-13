package com.sangjae.section01.autowired.subsection03.setter;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {
        ApplicationContext applicationContext
                = new AnnotationConfigApplicationContext("com/sangjae/section01");

        BookService bookService = applicationContext.getBean("bookServiceSetter", BookService.class);

        System.out.println(bookService.selectAllBooks());
        System.out.println(bookService.searchBookBySequence(1));

    }

}
