package com.ohgiraffers.section02.provider;

import com.ohgiraffers.common.MenuDTO;
import com.ohgiraffers.common.SearchCriteria;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

import static com.ohgiraffers.common.Template.getSqlSession;

public class SelectBuilderService {
    public void testSimpleStatement() {
        SqlSession sqlSession = getSqlSession();
        SelectBuilderMapper mapper = sqlSession.getMapper(SelectBuilderMapper.class);

        List<MenuDTO> menuList = mapper.selectAllMenu();

        if(menuList != null && !menuList.isEmpty()) {
            menuList.forEach(System.out::println);
        } else {
            System.out.println("조회 결과가 존재하지 않습니다.");
        }

        sqlSession.close();
    }

    public void testDynamicStatement(SearchCriteria searchCriteria) {
        SqlSession sqlSession = getSqlSession();
        SelectBuilderMapper mapper = sqlSession.getMapper(SelectBuilderMapper.class);

        List<MenuDTO> menuList = mapper.searchMenuByNameOrCategory(searchCriteria);

        if(menuList != null && !menuList.isEmpty()) {
            menuList.forEach(System.out::println);

        } else {
            System.out.println("조회 결과가 존재하지 않습니다.");
        }

        sqlSession.close();
    }
}