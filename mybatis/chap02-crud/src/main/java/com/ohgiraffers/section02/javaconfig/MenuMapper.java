package com.ohgiraffers.section02.javaconfig;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public interface MenuMapper {

    @Results(id="menuResultMap" , value={
            @Result(id = true , property = "menuCode", column = "menu_code"),
            @Result(property = "menuName", column = "menu_name"),
            @Result(property = "menuPrice", column = "menu_price"),
            @Result(property = "categoryCode", column = "category_code"),
            @Result(property = "orderableStatus", column = "orderable_status"),
    })
    @Select(" select\n" +
            "            menu_code,\n" +
            "            menu_name,\n" +
            "            menu_price,\n" +
            "            category_code,\n" +
            "            orderable_status\n" +
            "        from tbl_menu\n" +
            "        order by menu_code")
    List<MenuDTO> selectAllMenu();

    @ResultMap("menuResultMap")  // 상단에 선언한  @Result 를 매핑
    @Select(" select\n" +
            "            menu_code,\n" +
            "            menu_name,\n" +
            "            menu_price,\n" +
            "            category_code,\n" +
            "            orderable_status\n" +
            "        from tbl_menu\n" +
            "        where menu_code = #{menuCode}")
    MenuDTO selectMenuByMenuCode(int menuCode);

    @Insert("insert into\n" +
            "            tbl_menu (menu_name, menu_price , category_code , orderable_status)\n" +
            "        values (#{menuName}, #{menuPrice}, #{categoryCode}, 'Y')")
    int insertMenu(MenuDTO menu);


    @Update(" update tbl_menu\n" +
            "        set\n" +
            "        menu_name = #{menuName},\n" +
            "        menu_price = #{menuPrice},\n" +
            "        category_code = #{categoryCode},\n" +
            "        orderable_status = 'Y'\n" +
            "        where\n" +
            "        menu_code = #{menuCode}")
    int updateMenu(MenuDTO menu);


    @Delete("delete from tbl_menu\n" +
            "        where\n" +
            "        menu_code = #{menuCode}")
    int deleteMenu(int menuCode);
}
