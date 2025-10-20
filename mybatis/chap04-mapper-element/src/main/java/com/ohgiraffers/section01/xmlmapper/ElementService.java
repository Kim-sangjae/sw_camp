package com.ohgiraffers.section01.xmlmapper;

import org.apache.ibatis.session.SqlSession;

import java.util.List;

import static com.ohgiraffers.common.Template.getSqlSession;

public class ElementService {
    public void selectResultMapTest() {
        SqlSession sqlSession = getSqlSession();
        ElementMapper mapper = sqlSession.getMapper(ElementMapper.class);

        List<MenuDTO> menuList = mapper.selectResultMap();

        if(menuList != null && !menuList.isEmpty()){
            menuList.forEach(System.out::println);
        }else {
            System.out.println("조회 결과 없음");
        }

        sqlSession.close();
    }

    public void selectResultMapAssociationTest() {
        SqlSession sqlSession = getSqlSession();
        ElementMapper mapper = sqlSession.getMapper(ElementMapper.class);

        List<MenuAndCategoryDTO> menuList = mapper.selectResultMapAssociationTest();

        if(menuList != null && !menuList.isEmpty()){
            menuList.forEach(System.out::println);
        }else {
            System.out.println("조회 결과 없음");
        }

        sqlSession.close();
    }

    public void selectResultMapCollectionTest() {
        SqlSession sqlSession = getSqlSession();
        ElementMapper mapper = sqlSession.getMapper(ElementMapper.class);

        List<CategoryAndMenuDTO> categoryList = mapper.selectResultMapCollectionTest();

        if(categoryList != null && !categoryList.isEmpty()){
            categoryList.forEach(System.out::println);
        }else {
            System.out.println("조회 결과 없음");
        }

        sqlSession.close();
    }
}
