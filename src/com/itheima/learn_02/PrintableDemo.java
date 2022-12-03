package com.itheima.learn_02;

public class PrintableDemo {
    public static void main(String[] args) {
        usePrintable(System.out::println);
    }

    private static void usePrintable(Printable p) {
        p.printInt(88);
    }
}
