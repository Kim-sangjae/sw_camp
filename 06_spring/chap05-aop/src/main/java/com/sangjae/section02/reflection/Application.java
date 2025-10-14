package com.sangjae.section02.reflection;

public class Application {
    public static void main(String[] args) {
        Class class1 = Account.class;
        System.out.println("class1 : " + class1);

        Class class2 = new Account().getClass();
        System.out.println("class2 : " + class2);

        try {
            Class class3 = Class.forName("com.sangjae.section02.reflection.Account");
            System.out.println("class3 : " + class3);

            double[] arr = {0.1,0.2};
            System.out.println("arr : " + arr);

            Class class4 = Class.forName("[D");
            Class class5 = double[].class;
            System.out.println("class4 : " + class4);
            System.out.println("class5 : " + class5);


        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
