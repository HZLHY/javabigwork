package com.itheima.learn_02;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MyInterfaceDemo {
    public static void main(String[] args){
        // 按照多态方式创建对象并使用
//        MyInterfaceImplOne my = new MyInterfaceImplOne();
//        my.show1();
//        my.show2();
//        my.test1();
//        my.test();
//        String s = getString(()->"nihao");
//        System.out.println(s);
//        Integer i = getInteger(()->666);
//        System.out.println(i);
        ArrayList<String> list = new ArrayList<>();
        list.add("林青霞");
        list.add("张曼玉");
        list.add("王祖贤");
        list.add("柳岩");
        list.add("张敏");
        list.add("张无忌");
        // 用Stream流来改进，筛选以张开头长度为3的数据
        list.stream().filter(s->s.startsWith("张")).filter(s->s.length()==3).forEach(System.out::println);
        operatorString("LHY",s->System.out.println(new StringBuilder(s).reverse().toString()));
        operatorString("LHY", System.out::println, s->System.out.println(new StringBuilder(s).reverse().toString()));
    }
    // 定义一个方法，用不同的方式消费同一个字符串两次
    private static void operatorString(String name,Consumer<String> con1,Consumer<String> con2){
        con1.accept(name);
        con2.accept(name);
        // 或者这样
        con1.andThen(con2).accept(name);
    }
    // 定义一个方法，消费一个字符串数据
    private static void operatorString(String name,Consumer<String> con){
        con.accept(name);
    }
    private static Integer getInteger(Supplier<Integer> sup){
        return sup.get();
    }
    private static String getString(Supplier<String> sup){
        return sup.get();
    }

}
