package com.springboot.design.proxy.jdk;

import com.springboot.design.proxy.ITrainStation;
import com.springboot.design.proxy.TrainSaleWindow;

/**
 * @ProjectName: idc-root
 * @Package: com.springboot.design.proxy.jdk
 * @ClassName: CustomerJdkProxy
 * @Author: bo.tang
 * @Description: 动态代理测试类
 * @Date: 2020/7/9 9:50
 * @Version: 1.0
 */
public class CustomerJdkProxy {
    public static void main(String[] args) throws Exception {
        ITrainStation target = new TrainSaleWindow();
        ITrainStation proxy = (ITrainStation) new JdkProxy(target).getProxyInstance();
        proxy.saleTicket(1);
        System.out.println(proxy.returnTotalTicketPrice());
    }
}
