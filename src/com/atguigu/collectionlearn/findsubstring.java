package com.atguigu.collectionlearn;

import java.util.Arrays;
import java.util.List;

// 如果存在多个长度相同的最大相同字串
// 此时先返回String[]，后面可以用集合中的ArrayList替换
public class findsubstring {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(getMaxSameSubString("abcwerthellolyuiodefabcdef", "cvhellolbnmabcdef")));
        List<int[]> arr1 = List.of(new int[]{123, 345});
        System.out.println(arr1.get(0)[0]);
    }

    public static String[] getMaxSameSubString(String str1, String str2) {
        if (str1 != null && str2 != null) {
            StringBuffer sBuffer = new StringBuffer();
            String maxString = (str1.length() > str2.length()) ? str1 : str2;
            String minString = (str1.length() > str2.length()) ? str2 : str1;
            int len = minString.length();
            for (int i = 0; i < len; i++) {
                for (int x = 0, y = len - i; y <= len; x++, y++) {
                    String subString = minString.substring(x, y);
                    if (maxString.contains(subString)) {
                        sBuffer.append(subString + ",");
                    }
                }
                if (sBuffer.length() != 0) {
                    break;
                }
            }
            String[] resSpilt = sBuffer.toString().replaceAll(",$", "").split(",");
            return resSpilt;
        }
        return null;
    }
}
