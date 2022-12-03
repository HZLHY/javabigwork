package com.itheima.learn_02;

public interface MyInterface {
    void show1();

    void show2();

    default void test() {
        System.out.println("Test2");
    }
}
