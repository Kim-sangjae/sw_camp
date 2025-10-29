package com.ohgiraffers.cqrs.product.command.application.dto.request;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProductCreateRequest {
    private final String productName;
    private final Long productPrice;
    private final String productDescription;
    private final Long categoryCode;
    private final Long productStock;
}
