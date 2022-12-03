package com.itheima.jdbc;

import com.itheima.pojo.Brand;
import com.itheima.utils.DruidUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BrandTest {
    public static void main(String[] args) throws Exception {
        List<Brand> BranList = new ArrayList<>();
        Connection con = DruidUtil.getCon();
        String sql = "select * from tb_brand;";
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            String brandName = rs.getString("brand_name");
            String companyName = rs.getString("company_name");
            int ordered = rs.getInt("ordered");
            String description = rs.getString("description");
            int status = rs.getInt("status");
            Brand brand = new Brand();
            brand.setId(id);
            brand.setBrandName(brandName);
            brand.setCompany_name(companyName);
            brand.setOrdered(ordered);
            brand.setDescription(description);
            brand.setStatus(status);

            BranList.add(brand);
        }
        System.out.println(BranList);
        rs.close();
        pstmt.close();
        con.close();
    }
}
