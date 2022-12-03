package com.itheima.learn_03;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class ReflectDemo1 {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        // 获取Class对象
        Class<?> c = Class.forName("com.itheima.learn_03.Student");
        // 获取构造对象
        Constructor<?> con = c.getConstructor();
        Object obj = con.newInstance();
        System.out.println(obj);
        Field nameField = c.getDeclaredField("name");
        nameField.setAccessible(true);//取消访问检查
        nameField.set(obj, "林青霞");
        System.out.println(obj);

        Field ageField = c.getDeclaredField("age");
        ageField.setAccessible(true);
        ageField.set(obj, 30);
        System.out.println(obj);

        Field addressField = c.getDeclaredField("address");
        addressField.setAccessible(true);
        addressField.set(obj, "西安");
        System.out.println(obj);

        Method m1 = c.getMethod("method1");
        m1.invoke(obj);
        Method m2 = c.getMethod("method2", String.class);
        m2.invoke(obj, "林青霞");
        Method m3 = c.getMethod("method3", String.class, int.class);
        String res = (String) m3.invoke(obj, "零全新", 30);
        System.out.println(res);
        Method m4 = c.getDeclaredMethod("function");
        m4.setAccessible(true);
        m4.invoke(obj);
        // 越过泛型检查
        ArrayList<Integer> array = new ArrayList<>();
        array.add(10);
        array.add(20);
        Class<? extends ArrayList> aClass = array.getClass();
        Method m = aClass.getMethod("add", Object.class);
        m.invoke(array, "hello");
        System.out.println(array);

    }
}
