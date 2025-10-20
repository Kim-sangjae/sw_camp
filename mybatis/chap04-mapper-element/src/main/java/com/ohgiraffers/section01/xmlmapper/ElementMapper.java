package com.ohgiraffers.section01.xmlmapper;

import java.util.List;

public interface ElementMapper {

    List<MenuDTO> selectResultMap();

    List<MenuAndCategoryDTO> selectResultMapAssociationTest();

    List<CategoryAndMenuDTO> selectResultMapCollectionTest();
}
