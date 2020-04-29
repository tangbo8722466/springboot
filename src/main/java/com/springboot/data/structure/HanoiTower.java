package com.springboot.data.structure;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @ClassName HanoiTower
 * @Author sangfor for tangbo
 * @Description 汉诺塔
 * @Date 2020/4/27 10:05
 * @Version 1.0.0
 **/
public class HanoiTower {
    public static void main(String[] args) throws IOException {
        boolean isInputFalse = true;
        int floor = 0;
        while (isInputFalse) {
            System.out.println("please input tower floor(/d [0 for exit]): ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String input = br.readLine();
            if (StringUtils.equalsIgnoreCase(input, "0")) {
                System.out.println("system exit !");
                System.exit(0);
            }
            try {
                floor = Integer.parseInt(input);
                if (floor > 0) {
                    isInputFalse = false;
                    System.out.println("input number correct \n");
                }
                else {
                    System.out.println("please input number > 0 !! \n");
                }
            } catch (Exception ex) {
                System.out.println("please input number !! \n");
            }
        }

        StringBuffer x = new StringBuffer();
        StringBuffer y = new StringBuffer();
        StringBuffer z = new StringBuffer();

        for (int i=1; i <= floor; i++) {
            x.append(i);
        }
        print(x);
    }

    /**
     * 移动一层
     * @param floor 当前层数
     * @param x
     * @param y
     * @param z
     */
    public static void move(int floor, StringBuffer x, StringBuffer y, StringBuffer z) {

    }

    public static void print(StringBuffer buffer){
        if (StringUtils.isBlank(buffer)) {
            return;
        }
        for (int i = 0; i<buffer.length(); i++) {
            System.out.println(buffer.charAt(i));
        }
    }
}
