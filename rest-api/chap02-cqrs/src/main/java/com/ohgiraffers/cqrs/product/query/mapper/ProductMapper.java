package com.ohgiraffers.cqrs.product.query.mapper;

import com.ohgiraffers.cqrs.product.query.dto.request.ProductSearchRequest;
import com.ohgiraffers.cqrs.product.query.dto.response.ProductDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {

    /* 상품 코드로 상품 하나 조회 */
    ProductDto selectProductByCode(Long productCode);

    /* 검색&페이징 적용한 상품 목록 조회 */
    List<ProductDto> selectProducts(ProductSearchRequest productSearchRequest);

    /* 페이징 속성 계산을 위한 전체 컨텐츠 개수 조회 */
    long countProducts(ProductSearchRequest productSearchRequest);
}
