package com.itheima.learn_03;

public class Student {
    private String name;
    int age;
    public String address;

    public Student(){}
    private Student(String name){
        this.name = name;
    }
    Student(String name,int age){
        this.name = name;
        this.age = age;
    }
    public Student(String name, int age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                '}';
    }
    private void function(){
        System.out.println("function");
    }
    public void method1(){
        System.out.println("method");
    }
    public void method2(String s){
        System.out.println("method2"+s);
    }
    public String method3(String s,int i){
        return s+","+i;
    }
}
