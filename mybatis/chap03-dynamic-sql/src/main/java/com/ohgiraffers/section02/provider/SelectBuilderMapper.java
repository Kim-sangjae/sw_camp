package com.ohgiraffers.section02.provider;

import com.ohgiraffers.common.MenuDTO;
import com.ohgiraffers.common.SearchCriteria;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

public interface SelectBuilderMapper {
    @SelectProvider(type=SelectBuilderProvider.class, method="selectAllMenu")
    List<MenuDTO> selectAllMenu();
    @SelectProvider(type = SelectBuilderProvider.class, method = "searchMenuByNameOrCategory")
    List<MenuDTO> searchMenuByNameOrCategory(SearchCriteria searchCriteria);
}