package com.sangjae.section03.properties.subsection01.properties;

import com.sangjae.common.Beverage;
import com.sangjae.common.Bread;
import com.sangjae.common.Product;
import com.sangjae.common.ShoppingCart;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

@Configuration
@PropertySource("section03/properties/subsection01/properties/product-info.properties")
public class ContextConfiguration {

    /* 치환자 문법 (placeholder) 을 이용해서 properties 설정 파일에 담긴 값을 가져와서 주입할 수 있다.
    * 양 옆에 공백이 있으면 값을 읽어오지 못하므로 주의하고,  : 뒤에 key 가 없을 경우의 대체값을 입력할 수 있다. */
    @Value("${bread.carpbread.name:걍붕어빵}")
    private String name;
    @Value("${bread.carpbread.price:0}")
    private int price;

    @Bean
    public Product carpBread() {
        return new Bread(name, price, new java.util.Date());
    }
    @Bean
    public Product milk(
            @Value("${beverage.milk.name}") String name,
            @Value("${beverage.milk.price}") int price,
            @Value("${beverage.milk.capacity}") int capacity
    ) {
        return new Beverage(name, price, capacity);
    }
    @Bean
    public Product water(
            @Value("${beverage.water.name}") String name,
            @Value("${beverage.water.price}") int price,
            @Value("${beverage.water.capacity}") int capacity
    ) {
        return new Beverage(name, price, capacity);
    }
    @Bean
    @Scope("prototype") // 필요 시 마다 새로운 인스턴스를 생성해서 반환
    public ShoppingCart cart() {
        return new ShoppingCart();
    }
}
