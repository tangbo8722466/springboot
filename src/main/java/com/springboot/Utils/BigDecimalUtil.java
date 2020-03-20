package com.springboot.Utils;

import org.apache.commons.math3.util.ArithmeticUtils;

public class BigDecimalUtil {
    public static void main(String[] args) {
        System.out.println(ArithmeticUtils.addAndCheck(1, 2));
        System.out.println(ArithmeticUtils.subAndCheck(1, 2));
        System.out.println(ArithmeticUtils.mulAndCheck(1, 2));
    }
}
