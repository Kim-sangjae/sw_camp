package com.sangjae.handlermethod;


// 커멘드 객체로 사용하기 위해서는 request의 name 속성값과 필드명이 일치하도록 작성해야한다
public class MenuDTO {
    private String name;
    private Integer price;
    private Integer categoryCode;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(Integer categoryCode) {
        this.categoryCode = categoryCode;
    }
}
