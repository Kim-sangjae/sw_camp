package com.ohgiraffers.cqrs.product.command.domain.aggregate;

public enum ProductStatus {
    USABLE,     // 주문 가능
    DISABLE,    // 주문 불가능
    DELETED     // 삭제 (soft delete)
}
