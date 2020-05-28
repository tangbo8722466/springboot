package com.springboot.Utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.util.ArithmeticUtils;

import java.math.BigDecimal;

public class BigDecimalUtil {
    public static void main(String[] args) {
        System.out.println(ArithmeticUtils.addAndCheck(1, 2));
        System.out.println(ArithmeticUtils.subAndCheck(1, 2));
        System.out.println(ArithmeticUtils.mulAndCheck(1, 2));
    }
    public static Long stringToLong(String numberStr){
        if (StringUtils.isBlank(numberStr)) {
            return 0L;
        }
        String regex1 = "-?[0-9]*+.[0-9]*";
        String regex2 = "-?[0-9]*";
        if (numberStr.matches(regex1)) {
            BigDecimal decimal = new BigDecimal(numberStr).multiply(new BigDecimal(100));
            return decimal.longValue();
        }

        if (numberStr.matches(regex2)) {
            return Long.parseLong(numberStr);
        }
        return 0L;
    }
}
