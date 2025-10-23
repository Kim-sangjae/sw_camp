package com.sangjae.chap03asosiationmapping.section02.onetomany;

import jakarta.persistence.*;

import java.util.List;

@Entity(name = "category_and_menu")
@Table(name = "tbl_category")
public class Category {

    @Id
    private int categoryCode;

    private String categoryName;

    private Integer refCategoryCode;

    // OneToMany 관계에서 fetch 속성은 LAZY로 설종되어있음
    // 필요한 시점에만 조회해서온다
    // 즉시 조회및 조인해서 가져올거면 FetchType.EAGER 로 설정
    @JoinColumn(name="categoryCode")
    @OneToMany(cascade=CascadeType.PERSIST , fetch = FetchType.EAGER)
    private List<Menu> menuList;

    protected Category() {}

    public Category(
            int categoryCode, String categoryName,
            Integer refCategoryCode, List<Menu> menuList
    ) {
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
        this.refCategoryCode = refCategoryCode;
        this.menuList = menuList;
    }

    public int getCategoryCode() {
        return categoryCode;
    }
    public void setCategoryCode(int categoryCode) {
        this.categoryCode = categoryCode;
    }
    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public Integer getRefCategoryCode() {
        return refCategoryCode;
    }
    public void setRefCategoryCode(Integer refCategoryCode) {
        this.refCategoryCode = refCategoryCode;
    }
    public List<Menu> getMenuList() {
        return menuList;
    }
    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }
    @Override
    public String toString() {
        return "CategoryAndMenu [categoryCode=" + categoryCode +
                ", categoryName=" + categoryName +
                ", refCategoryCode=" + refCategoryCode +
                ", menuList=" + menuList + "]";
    }
}