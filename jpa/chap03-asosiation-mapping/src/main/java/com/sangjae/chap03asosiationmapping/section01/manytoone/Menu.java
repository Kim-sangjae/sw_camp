package com.sangjae.chap03asosiationmapping.section01.manytoone;

import jakarta.persistence.*;

@Entity(name = "menu_and_category")
@Table(name = "tbl_menu")
public class Menu {

    @Id
    private int menuCode;

    private String menuName;

    private int menuPrice;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "categoryCode")
    private Category category;

    private String orderableStatus;

    protected Menu() {}

    public Menu(
            int menuCode, String menuName, int menuPrice,
            Category category, String orderableStatus
    ) {
        this.menuCode = menuCode;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.category = category;
        this.orderableStatus = orderableStatus;
    }

    public int getMenuCode() {
        return menuCode;
    }

    public String getMenuName() {
        return menuName;
    }

    public int getMenuPrice() {
        return menuPrice;
    }

    public Category getCategory() {
        return category;
    }

    public String getOrderableStatus() {
        return orderableStatus;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "menuCode=" + menuCode +
                ", menuName='" + menuName + '\'' +
                ", menuPrice=" + menuPrice +
                ", category=" + category +
                ", orderableStatus='" + orderableStatus + '\'' +
                '}';
    }
}