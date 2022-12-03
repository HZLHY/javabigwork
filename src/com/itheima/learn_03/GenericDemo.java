package com.itheima.learn_03;

import java.util.ArrayList;
import java.util.List;

public class GenericDemo {
    public static void main(String[] args){
        Student s = new Student();
        s.setName("LHY");
        Generic<String> g1 = new Generic<>();
        g1.setT("LHY");
        System.out.println(g1.getT()+ s.getName());
        List<?> list1 = new ArrayList<Integer>();
    }
}
