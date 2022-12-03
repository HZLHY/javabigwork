package com.itheima.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class JDBCDemo {
    public static void main(String[] args) throws Exception{
        //1.注册驱动   MySQL5后可以省略注册驱动步骤
//        Class.forName("com.mysql.cj.jdbc.Driver"); // 加载Driver类的时候就已经把其中静态代码块执行了
        //2.获取链接
        String url = "jdbc:mysql://localhost:3306/jdbc?useSSL=false&serverTimezone=UTC";
        String username = "root";
        String password = "root";
        Connection conn = DriverManager.getConnection(url,username,password);
        //3.定义sql
        String sql = "update stu set age = 250 where id = 8";
        //4.获取执行sql的对象Statement
        Statement statement = conn.createStatement();
        //5.执行sql
        int count = statement.executeUpdate(sql);
        System.out.println(count);
        //7.释放资源
        statement.close();
        conn.close();
    }
}
