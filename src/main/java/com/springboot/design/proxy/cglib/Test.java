package com.springboot.design.proxy.cglib;

import com.springboot.design.proxy.TrainSaleWindow;

/**
 * @ProjectName: springboot
 * @Package: com.springboot.design.proxy.cglib
 * @ClassName: Test
 * @Author: bo.tang
 * @Description: 测试类
 * @Date: 2020/7/9 10:06
 * @Version: 1.0
 */
public class Test {
    public static void main(String[] args) {
        TrainSaleWindow target = new TrainSaleWindow();
        TrainSaleWindow proxy = (TrainSaleWindow) new CglibProxy(target).getProxyInstance();
        proxy.saleTicket(1);
        System.out.println(proxy.returnTotalTicketPrice());
    }
}
