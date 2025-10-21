package com.ohgiraffers.section02.crud;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class EntityManagerCRUDTest {


    private EntityManagerCRUD entityManagerCRUD;

    @BeforeEach
    void init(){
        entityManagerCRUD = new EntityManagerCRUD();
    }

    @DisplayName("메뉴 코드로 메뉴 조회")
    @ParameterizedTest// 파라미터값 여러번 체크하기 위한 어노테이션
    @CsvSource({"3,3","4,4","5,5"}) // , 단위로 파라미터 값설정할수 있는 어노테이션
    void testFindMenuByMenuCode(int menuCode, int expected){
        //when
        Menu foundMenu = entityManagerCRUD.findMenuByMenuCode(menuCode);

        //then
        assertEquals(expected,foundMenu.getMenuCode());
        System.out.println("foundMenu = " + foundMenu);
    }



    private static Stream<Arguments> newMenu(){
        return Stream.of(
                Arguments.of("신메뉴",3500,4,"Y")
        );
    }

    @DisplayName("새로운 메뉴 추가")
    @ParameterizedTest
    @MethodSource("newMenu") // 파라미터의 값들을 지정한 메서드 형식으로 받겠다
    void testRegist(String menuName , int menuPrice , int categoryCode , String orderableStatus){
        // when
        Menu menu = new Menu(menuName,menuPrice,categoryCode,orderableStatus);
        Long count = entityManagerCRUD.saveAndReturnAllCount(menu);

        System.out.println("count = " + count);
    }



    @DisplayName("메뉴 이름 수정")
    @ParameterizedTest
    @CsvSource("1,변경 된 이름")
    void testModifyMenuName(int menuCode,String menuName){
        //when
        Menu modifyMenu = entityManagerCRUD.modifyMenuName(menuCode,menuName);

        //then
        assertEquals(menuName,modifyMenu.getMenuName());
    }


    @DisplayName("메뉴 코드로 삭제 테스트")
    @ParameterizedTest
    @ValueSource(ints = {6,7})
    void testDeleteMenu(int menuCode){
        //when
        Long count = entityManagerCRUD.removeAndResultAllCount(menuCode);
        //then
        System.out.println("count = " + count);
    }









}