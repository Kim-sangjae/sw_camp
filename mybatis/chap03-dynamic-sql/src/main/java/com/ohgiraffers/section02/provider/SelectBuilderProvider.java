package com.ohgiraffers.section02.provider;

import com.ohgiraffers.common.SearchCriteria;
import org.apache.ibatis.jdbc.SQL;

public class SelectBuilderProvider {

    public String selectAllMenu() {
        return new SQL()
                .SELECT("menu_code")
                .SELECT("menu_name")
                .SELECT("menu_price")
                .SELECT("category_code")
                .SELECT("orderable_status")
                .FROM("tbl_menu")
                .WHERE("orderable_status = 'Y'")
                .toString();
    }

    public String searchMenuByNameOrCategory(SearchCriteria searchCriteria) {
        SQL sql = new SQL();
        sql.SELECT("menu_code", "menu_name", "menu_price", "category_code", "orderable_status")
                .FROM("tbl_menu");

        if("category".equals(searchCriteria.getCondition())) {
            sql.JOIN("tbl_category USING (category_code)")
                    .WHERE("orderable_status = 'Y'")
                    .AND()
                    .WHERE("category_name = #{ value }");
        } else if("name".equals(searchCriteria.getCondition())) {
            // where 메소드의 가변인자로 전달하는 경우 자동적으로 AND 조건 처리 되므로
            // OR 의 경우 별도 메소드 사용
            sql.WHERE(
                    "orderable_status = 'Y'"
                    , "menu_name LIKE CONCAT('%', #{ value }, '%')"
            );
        }
        return sql.toString();
    }
}