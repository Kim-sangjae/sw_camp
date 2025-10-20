package com.ohgiraffers.section02.provider;

import com.ohgiraffers.common.MenuDTO;
import org.apache.ibatis.annotations.*;

public interface SqlBuilderMapper {
    @InsertProvider(type = SqlBuilderProvider.class,method = "insertMenu")
    int insertMenu(MenuDTO menuDTO);

    @UpdateProvider(type = SqlBuilderProvider.class, method = "updateMenu")
    int updateMenu(MenuDTO menuDTO);

    @DeleteProvider(type = SqlBuilderProvider.class , method = "deleteMenu")
    int deleteMenu(int menuCode);
}
