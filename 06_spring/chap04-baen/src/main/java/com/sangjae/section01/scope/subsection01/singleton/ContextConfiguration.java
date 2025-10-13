package com.sangjae.section01.scope.subsection01.singleton;

import com.sangjae.common.Beverage;
import com.sangjae.common.Bread;
import com.sangjae.common.Product;
import com.sangjae.common.ShoppingCart;
import org.springframework.context.annotation.*;

@Configuration
public class ContextConfiguration {

    @Bean
    @DependsOn({"carpBread" , "milk" ,"water"}) // 나열한 빈 인스턴스가 생성 된 뒤에 생성되도록 설정
    @Lazy // 컨테이너 동작 시점이 아니라 해당 객체가 필요한 시점에 인스턴스 생성
    @Scope("singleton") //기본값
    public ShoppingCart cart() {
        System.out.println("쇼핑카트 생성 시점");

        return new ShoppingCart();
    }


    @Bean
    public Product carpBread() {
        System.out.println("붕어빵 생성 시점");
        return new Bread("붕어빵", 1000, new java.util.Date());
    }
    @Bean
    public Product milk() {
        System.out.println("딸기우유 생성 시점");

        return new Beverage("딸기우유", 1500, 500);
    }
    @Bean
    public Product water() {
        System.out.println("지리산 암반수 생성 시점");

        return new Beverage("지리산암반수", 3000, 500);
    }

}

