package com.ohgiraffers.transactionlecture.service;

import com.ohgiraffers.transactionlecture.domain.Order;
import com.ohgiraffers.transactionlecture.domain.OrderMenu;
import com.ohgiraffers.transactionlecture.mapper.OrderMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    private final OrderMapper orderMapper;

    public OrderService(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    /* 주문 등록 기능 메소드
    * 주문 정보(tbl_order) 테이블과 주문별 메뉴 정보(tbl_order_menu)를 하나의
    * 트랜잭션으로 처리한다. */
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void registerOrder(Order order, List<OrderMenu> orderMenus) {
        // 1. tbl_order 에 주문 정보 삽입
        orderMapper.insertOrder(order);

        // 주문 등록 후 auto generated 된 orderCode를 각 주문 메뉴에 설정
        orderMenus.forEach(
                orderMenu -> orderMenu.setOrderCode(order.getOrderCode())
        );

        // 2. tbl_order_menu 에 주문별 메뉴 정보 삽입
        orderMenus.forEach(orderMapper::insertOrderMenu);
    }
}
