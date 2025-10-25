package com.sangjae.chap06springdatajpa.main.repository;



import com.sangjae.chap06springdatajpa.main.entity.Menu;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MenuRepository extends JpaRepository<Menu, Integer> {
    // 전달받은 가격을 초과하는 메뉴 목록 조회
    List<Menu> findByMenuPriceGreaterThan(Integer menuPrice);
    // + 가격 오름차순
    List<Menu> findByMenuPriceGreaterThanOrderByMenuPrice(Integer menuPrice);
    // + 전달받은 정렬기준
    List<Menu> findByMenuPriceGreaterThan(Integer menuPrice, Sort sort);
    List<Menu> findByMenuNameContaining(String menuName);

}
