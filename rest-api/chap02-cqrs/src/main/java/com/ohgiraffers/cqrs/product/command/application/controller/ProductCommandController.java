package com.ohgiraffers.cqrs.product.command.application.controller;

import com.ohgiraffers.cqrs.common.dto.ApiResponse;
import com.ohgiraffers.cqrs.product.command.application.dto.request.ProductCreateRequest;
import com.ohgiraffers.cqrs.product.command.application.dto.request.ProductUpdateRequest;
import com.ohgiraffers.cqrs.product.command.application.dto.response.ProductCommandResponse;
import com.ohgiraffers.cqrs.product.command.application.service.ProductCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class ProductCommandController {

    private final ProductCommandService productCommandService;

    /* 파일 업로드 요청 (multipart) 시에 JSON + 파일의 형태로 전달 받기 위해 @RequestPart 어노테이션 사용 */
    @PostMapping(value = "/products", consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse<ProductCommandResponse>> createProduct(
            @RequestPart ProductCreateRequest productCreateRequest,
            @RequestPart MultipartFile productImg
    ) {
        Long productCode = productCommandService.createProduct(productCreateRequest, productImg);
        ProductCommandResponse response = ProductCommandResponse.builder()
                .productCode(productCode)
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }

    @PutMapping(value = "/products/{productCode}", consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse<Void>> updateProduct(
            @PathVariable Long productCode,
            @RequestPart ProductUpdateRequest productUpdateRequest,
            @RequestPart(required = false) MultipartFile productImg
            ) {
        productCommandService.updateProduct(productCode, productUpdateRequest, productImg);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @DeleteMapping("/products/{productCode}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(
            @PathVariable Long productCode
    ){
        productCommandService.deleteProduct(productCode);
        return ResponseEntity.ok(ApiResponse.success(null));
    }













}
