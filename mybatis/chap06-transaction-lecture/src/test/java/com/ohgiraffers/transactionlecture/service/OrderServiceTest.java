package com.ohgiraffers.transactionlecture.service;

import com.ohgiraffers.transactionlecture.domain.Order;
import com.ohgiraffers.transactionlecture.domain.OrderMenu;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/* 통합 테스트 */
@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    // Spring Framework에서 제공하는 클래스로 JDBC를 이용한 데이터베이스 접근을 쉽게 해주는 도구
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testRegisterOrder() {
        // given
        Order order = new Order();
        order.setOrderDate("20251021");
        order.setOrderTime("100430");
        order.setTotalOrderPrice(50000);

        OrderMenu menu1 = new OrderMenu();
        menu1.setMenuCode(3);
        menu1.setOrderAmount(1);
        OrderMenu menu2 = new OrderMenu();
        menu2.setMenuCode(4);
        menu2.setOrderAmount(2);
        List<OrderMenu> orderMenus = Arrays.asList(menu1, menu2);

        // when
        orderService.registerOrder(order, orderMenus);

        // then
        Integer orderCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM tbl_order", Integer.class
        );
        Integer orderMenuCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM tbl_order_menu", Integer.class
        );
        assertThat(orderCount).isEqualTo(1);
        assertThat(orderMenuCount).isEqualTo(2);

        String orderDate = jdbcTemplate.queryForObject(
                "SELECT order_date FROM tbl_order", String.class
        );
        assertThat(orderDate).isEqualTo("20251021");
    }

}