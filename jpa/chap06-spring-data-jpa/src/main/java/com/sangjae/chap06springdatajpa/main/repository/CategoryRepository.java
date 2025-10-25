package com.sangjae.chap06springdatajpa.main.repository;

import com.sangjae.chap06springdatajpa.main.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    /* JPQL or Native Query 를 작성하는 방법 */
    @Query(value = "SELECT c FROM Category c " +
            "WHERE c.refCategoryCode IS NOT NULL ORDER BY c.categoryCode")
    /*@Query(value = "SELECT category_code, category_name, ref_category_code " +
            "FROM tbl_category WHERE ref_category_code IS NOT NULL " +
            "ORDER BY category_code", nativeQuery = true)*/
    List<Category> findAllCategory();
}
