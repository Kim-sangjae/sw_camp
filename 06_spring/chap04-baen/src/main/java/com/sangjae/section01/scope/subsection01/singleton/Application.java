package com.sangjae.section01.scope.subsection01.singleton;

import com.sangjae.common.Beverage;
import com.sangjae.common.Bread;
import com.sangjae.common.Product;
import com.sangjae.common.ShoppingCart;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {

    public static void main(String[] args) {

        /* 빈 설정 파일을 기반으로 IoC 컨테이너 생성 */
        ApplicationContext context = new AnnotationConfigApplicationContext(ContextConfiguration.class);
        /* 붕어빵, 딸기우유, 지리산 암반수 등의 빈 객체를 반환 받는다. */
        Product carpBread = context.getBean("carpBread", Bread.class);
        Product milk = context.getBean("milk", Beverage.class);
        Product water = context.getBean("water", Beverage.class);

        System.out.println("쇼핑 카트 객체 꺼내기 전");

        /* 첫 번째 손님이 쇼핑 카트를 꺼낸다. */
        ShoppingCart cart1 = context.getBean("cart", ShoppingCart.class);
        cart1.addItem(carpBread);
        cart1.addItem(milk);
        /* 붕어빵과 딸기우유가 담겨있다. */
        System.out.println("cart1에 담긴 내용 : " + cart1.getItem());
        /* 두 번째 손님이 쇼핑 카트를 꺼낸다. */
        ShoppingCart cart2 = context.getBean("cart", ShoppingCart.class);
        cart2.addItem(water);
        /* 붕어빵과 딸기우유와 지리산암반수가 담겨있다. */
        System.out.println("cart2에 담긴 내용 : " + cart2.getItem());
        /* 두 카드의 hashcode를 출력해보면 동일한 것을 볼 수 있다. */
        System.out.println("cart1의 hashcode : " + cart1.hashCode());
        System.out.println("cart2의 hashcode : " + cart2.hashCode());

    }
}
