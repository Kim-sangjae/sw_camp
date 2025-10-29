package com.ohgiraffers.cqrs.product.command.application.service;

import com.ohgiraffers.cqrs.common.storage.FileStorage;
import com.ohgiraffers.cqrs.exception.BusinessException;
import com.ohgiraffers.cqrs.exception.ErrorCode;
import com.ohgiraffers.cqrs.product.command.application.dto.request.ProductCreateRequest;
import com.ohgiraffers.cqrs.product.command.application.dto.request.ProductUpdateRequest;
import com.ohgiraffers.cqrs.product.command.domain.aggregate.Product;
import com.ohgiraffers.cqrs.product.command.domain.aggregate.ProductStatus;
import com.ohgiraffers.cqrs.product.command.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProductCommandService {

    private final ProductRepository productRepository;
    private final FileStorage fileStorage;
    private final ModelMapper modelMapper;

    @Value("${image.image-url}")
    private String IMAGE_URL;

    /* 상품 등록 */
    @Transactional
    public Long createProduct(ProductCreateRequest productCreateRequest, MultipartFile productImg) {

        // 파일을 먼저 저장하고 실패 시 DB 작업을 수행하지 않음
        final String newFileName = fileStorage.store(productImg);

        // DTO to Entity
        Product newProduct = modelMapper.map(productCreateRequest, Product.class);
        // 저장 된 이미지를 요청할 url 설정
        newProduct.changeProductImageUrl(IMAGE_URL + newFileName);

        // Entity 저장
        Product saved = productRepository.save(newProduct);

        // 로직 롤백 될 경우 새 파일 제거 -> 롤백 보상
        // TransactionSynchronizationManager : 스프링 트랜잭션이 라이프사이클 이벤트에
        // 외부 로직(파일 삭제, 로그 기록) 등을 안전하게 연결할 수 있는 훅을 제공
        TransactionSynchronizationManager.registerSynchronization(
                // registerSynchronization 메소드를 통해 TransactionSynchronization 구현체
                // 를 등록하면 트랜잭션의 커밋 또는 롤백 직후 로직을 정의할 수 있음
                new TransactionSynchronization() {
                    @Override
                    public void afterCompletion(int status) {
                        if(status != STATUS_COMMITTED) {
                            fileStorage.deleteQuietly(newFileName);
                        }
                    }
                }
        );

        return saved.getProductCode();
    }

    /* 상품 수정 */
    @Transactional
    public void updateProduct(Long productCode, ProductUpdateRequest productUpdateRequest, MultipartFile productImg) {

        // Product Entity 조회
        Product product = productRepository.findById(productCode)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        final String oldUrl = product.getProductImageUrl();
        final String oldFileName = extractFileName(oldUrl);

        String newFileName = null;
        if(productImg != null && !productImg.isEmpty()) {
            // 파일이 전달 되었다면 새로 저장
            newFileName = fileStorage.store(productImg);
            // Entity에서 image url 수정
            product.changeProductImageUrl(IMAGE_URL + newFileName);
        }

        // 나머지 수정을 위해 전달 된 값을 Entity에서 변경
        product.updateProduct(
                productUpdateRequest.getProductName(),
                productUpdateRequest.getProductPrice(),
                productUpdateRequest.getProductDescription(),
                productUpdateRequest.getCategoryCode(),
                productUpdateRequest.getProductStock(),
                ProductStatus.valueOf(productUpdateRequest.getStatus())
        );

        final String finalNew = newFileName;
        // 커밋 되면 이전 파일 삭제, 롤백 되면 새 파일 삭제 처리
        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronization() {
                    @Override
                    public void afterCompletion(int status) {
                        if(status == STATUS_COMMITTED) {
                            if(finalNew != null && oldFileName != null) fileStorage.deleteQuietly(oldFileName);
                        } else {
                            if(finalNew != null) fileStorage.deleteQuietly(finalNew);
                        }
                    }
                }
        );
    }

    private String extractFileName(String url) {
        if (url == null) return null;
        int idx = url.lastIndexOf('/');
        return (idx >= 0 && idx < url.length() - 1) ? url.substring(idx + 1) : url;
    }



    /* 상품 삭제 (soft delete)*/
    /* Entity 에 @SQLDelete 를 통해 삭제메서드 요청 ( 상태값 update ) */
    @Transactional
    public void deleteProduct(Long productCode) {
     productRepository.deleteById(productCode);
    }

}
