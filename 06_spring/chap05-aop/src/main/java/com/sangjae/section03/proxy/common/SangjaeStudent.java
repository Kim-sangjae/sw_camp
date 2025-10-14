package com.sangjae.section03.proxy.common;

public class SangjaeStudent implements Student{

    @Override
    public void study(int hours) {
        System.out.println(hours + "시간 동안 공부합니다");
    }


}
