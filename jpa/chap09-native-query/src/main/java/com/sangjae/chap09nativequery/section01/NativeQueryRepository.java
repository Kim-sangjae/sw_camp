package com.sangjae.chap09nativequery.section01;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NativeQueryRepository {

    @PersistenceContext
    private EntityManager manager;



    public Menu nativeQueryByResultType(int menuCode) {

        // Native Query 의 수행 결과를 엔티티에 매핑 시키기 위해서는 반드시 모든 컬럼이 포홤되어야 한다
        String query
                = "SELECT" +
                " menu_code, menu_name, menu_price, category_code, orderable_status" +
                " FROM tbl_menu" +
                " WHERE menu_code = ?";
        Query nativeQuery
                = manager.createNativeQuery(query, Menu.class)
                .setParameter(1, menuCode);
        return (Menu) nativeQuery.getSingleResult();
    }



    // 일부 컬럼만 사용 할 경우 엔티티랑 매핑시키기는 어렵다 (Menu)
    public List<Object[]> nativeQueryByNoResultType() {
        String query = "SELECT menu_name, menu_price FROM tbl_menu";
        return manager.createNativeQuery(query).getResultList();
    }



    public List<Object[]> nativeQueryByAutoMapping() {
        String query
                = "SELECT a.category_code, a.category_name, a.ref_category_code," +
                " COALESCE(v.menu_count, 0) menu_count" +
                " FROM tbl_category a" +
                " LEFT JOIN (SELECT COUNT(*) AS menu_count, b.category_code" +
                " FROM tbl_menu b" +
                " GROUP BY b.category_code) v ON (a.category_code = v.category_code)" +
                " ORDER BY 1";

        Query nativeQuery
                = manager.createNativeQuery(query, "categoryCountAutoMapping");
        return nativeQuery.getResultList();
    }


}
