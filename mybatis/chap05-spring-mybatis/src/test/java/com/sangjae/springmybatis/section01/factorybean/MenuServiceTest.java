package com.sangjae.springmybatis.section01.factorybean;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// 단위 테스트 (Unit Test)
// Mockito 를 활용하여 의존성을 모킹(Mocking) 한 후 MenuService의 순수 비즈니스 로직을 검증
@ExtendWith(MockitoExtension.class)
class MenuServiceTest {

    // @Mock : 모의 객체로 설정
    @Mock
    private SqlSessionTemplate sqlSessionTemplate; // 실제 DB 연결 없이 메서드 호출 처리 가능

    @Mock
    private MenuMapper menuMapper; // mybatis Mapper 구현체 없이 메서드 호출 가능

    // @InjectMocks : 모의 객체를 MenuService에 주입하여 내부 의존성이 대체된다
    @InjectMocks
    private MenuService menuService;

    @BeforeEach
    public void setUp(){
        // sqlSessionTemplate.getMapper 호출시에 모의 객체 menuMapper를 반환하도록 설정
        when(sqlSessionTemplate.getMapper(MenuMapper.class)).thenReturn(menuMapper);
    }

    @Test
    public void testFindAllMenuByOrderableStatus(){
        // given
        String orderableStatus = "Y";
        MenuDTO menu1 = new MenuDTO(1,"김치찌개",8000,4,"Y");
        MenuDTO menu2 = new MenuDTO(2,"된장찌개",9000,4,"Y");
        List<MenuDTO> originalList = Arrays.asList(menu1,menu2);

        when(menuMapper.findAllMenuByOrderableStatus(orderableStatus)).thenReturn(originalList);

        // when
        List<MenuDTO> resultList = menuService.findAllMenuByOrderableStatus(orderableStatus);

        // then
        // 주어진 결과 갑이 올바른 비즈니스로직으로 가공 되어있는지 확인
        assertEquals("김치찌개 (주문 가능)",resultList.get(0).getMenuName());
        assertEquals("된장찌개 (주문 가능)",resultList.get(1).getMenuName());

        // 해당 객체에서 메서드 호출 여부 확인 -> 서비스 내부의 상호작용이 기대한대로 이루어졌는지
        verify(sqlSessionTemplate).getMapper(MenuMapper.class);
        verify(menuMapper).findAllMenuByOrderableStatus(orderableStatus);
    }



    @Test
    public void testFindAllMenuByOrderableStatus_NotOrderable(){
        // given
        String orderableStatus = "Y";
        MenuDTO menu1 = new MenuDTO(1,"김치찌개",8000,4,"N");
        MenuDTO menu2 = new MenuDTO(2,"된장찌개",9000,4,"N");
        List<MenuDTO> originalList = Arrays.asList(menu1,menu2);

        when(menuMapper.findAllMenuByOrderableStatus(orderableStatus)).thenReturn(originalList);

        // when
        List<MenuDTO> resultList = menuService.findAllMenuByOrderableStatus(orderableStatus);

        // then
        // 주어진 결과 갑이 올바른 비즈니스로직으로 가공 되어있는지 확인
        assertEquals("김치찌개 (주문 불가능)",resultList.get(0).getMenuName());
        assertEquals("된장찌개 (주문 불가능)",resultList.get(1).getMenuName());

        // 해당 객체에서 메서드 호출 여부 확인 -> 서비스 내부의 상호작용이 기대한대로 이루어졌는지
        verify(sqlSessionTemplate).getMapper(MenuMapper.class);
        verify(menuMapper).findAllMenuByOrderableStatus(orderableStatus);
    }


}