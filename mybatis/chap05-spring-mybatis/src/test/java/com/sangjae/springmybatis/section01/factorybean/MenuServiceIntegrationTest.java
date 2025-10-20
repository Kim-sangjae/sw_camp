package com.sangjae.springmybatis.section01.factorybean;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// 통합테스트
// 실제 스프링 컨텍스트를 로딩하여 MenuService 의 전체 동작 (실제 DB와 연동 및 로직수행)
// 을 검증하는 테스트
@SpringBootTest
@ActiveProfiles("test") // 테스트 실행 시 application-test.yml 설정을 사용
@Transactional // 테스트 수행 후에 DB 반영 된 변경 사항 롤백
@Sql(statements = {
        //테스트용 데이터 삽입
        "INSERT INTO tbl_menu VALUES (null, '열무김치라떼', 4500, 8, 'Y')",
        "INSERT INTO tbl_menu VALUES (null, '우럭스무디', 5000, 10, 'N')"
})
public class MenuServiceIntegrationTest {

    @Autowired
    private MenuService menuService;


    @Test
    public void testFindAllMenuByOrderableStatus_Orderable(){
        //given
        String orderableStatus = "Y";

        // when
        List<MenuDTO> resultList = menuService.findAllMenuByOrderableStatus(orderableStatus);

        // then
        assertEquals(1,resultList.size());
        resultList.forEach(menu ->{
            assertTrue(menu.getMenuName().endsWith("(주문 가능)"),
                    "주문 가능 메뉴의 이름은 '(주문 가능)' 으로 끝나야 합니다");
        });
    }



    @Test
    public void testFindAllMenuByOrderableStatus_NotOrderable(){
        //given
        String orderableStatus = "N";

        // when
        List<MenuDTO> resultList = menuService.findAllMenuByOrderableStatus(orderableStatus);

        // then
        assertEquals(1,resultList.size());
        resultList.forEach(menu ->{
            assertTrue(menu.getMenuName().endsWith("(주문 불가능)"),
                    "주문 불가능 메뉴의 이름은 '(주문 불가능)' 으로 끝나야 합니다");
        });
    }

}
