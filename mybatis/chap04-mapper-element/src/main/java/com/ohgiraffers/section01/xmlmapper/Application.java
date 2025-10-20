package com.ohgiraffers.section01.xmlmapper;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        ElementService elementService = new ElementService();

        Scanner sc = new Scanner(System.in);

        do{
            System.out.println("==== Mapper Element 테스트 ====");
            System.out.println("1. <resultMap> 테스트");
            System.out.println("2. <association> 테스트");
            System.out.println("3. <collection> 테스트");
            System.out.println("메뉴 번호 : ");

            int no = sc.nextInt();

            switch(no){
                case 1 : elementService.selectResultMapTest(); break;
                case 2 : elementService.selectResultMapAssociationTest(); break;
                case 3 : elementService.selectResultMapCollectionTest(); break;
                default:
                    System.out.println("잘못 된 번호 입력");

            }
        } while (true);
    }
}
