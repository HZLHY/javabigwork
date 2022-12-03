package com.itheima;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class RunoobTest {
    public static void main(String[] args) {
        List<String[]> rowList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/com/itheima/1.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] lineItems = line.split(",");
                rowList.add(lineItems);
            }
            String[][] matrix = new String[rowList.size()][];

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
