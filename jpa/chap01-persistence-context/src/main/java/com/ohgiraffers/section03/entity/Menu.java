package com.ohgiraffers.section03.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/* 영속성 컨텍스트에서 관리되는 엔터티명은 기본적으로는 클래스명,
 * 단, 중복되면 안되므로 여기서는 명시함 */
@Entity(name = "Section03Menu")
@Table(name = "tbl_menu")
public class Menu {
    /* 영속성 컨텍스트에서 엔터티는 key, value의 형태로 관리되며 key의 역할을 할 값에
    @Id 어노테이션을 붙인다. 해당 어노테이션은 필수이다. */
    @Id
    @Column(name="menu_code")
    private int menuCode;
    @Column(name="menu_name")
    private String menuName;
    @Column(name="menu_price")
    private int menuPrice;
    @Column(name="category_code")
    private int categoryCode;
    @Column(name="orderable_status")
    private String orderableStatus;

    public Menu() {

    }

    public Menu(int menuCode, String menuName, int menuPrice, int categoryCode, String orderableStatus) {
        this.menuCode = menuCode;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.categoryCode = categoryCode;
        this.orderableStatus = orderableStatus;
    }

    public int getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(int menuCode) {
        this.menuCode = menuCode;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public int getMenuPrice() {
        return menuPrice;
    }

    public void setMenuPrice(int menuPrice) {
        this.menuPrice = menuPrice;
    }

    public int getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(int categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getOrderableStatus() {
        return orderableStatus;
    }

    public void setOrderableStatus(String orderableStatus) {
        this.orderableStatus = orderableStatus;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "menuCode=" + menuCode +
                ", menuName='" + menuName + '\'' +
                ", menuPrice=" + menuPrice +
                ", categoryCode=" + categoryCode +
                ", orderableStatus='" + orderableStatus + '\'' +
                '}';
    }

}
