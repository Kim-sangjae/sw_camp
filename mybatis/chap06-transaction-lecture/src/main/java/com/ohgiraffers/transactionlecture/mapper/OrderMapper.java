package com.ohgiraffers.transactionlecture.mapper;

import com.ohgiraffers.transactionlecture.domain.Order;
import com.ohgiraffers.transactionlecture.domain.OrderMenu;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {
    void insertOrder(Order order);

    void insertOrderMenu(OrderMenu orderMenu);
}
