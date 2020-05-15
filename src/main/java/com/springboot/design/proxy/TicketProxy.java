package com.springboot.design.proxy;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName TicketProxy
 * @Author sangfor for tangbo
 * @Description
 * 代理类
 * @Date 2020/5/15 15:29
 * @Version 1.0.0
 **/
@Slf4j
public class TicketProxy implements TrainStation{
    /*手续费*/
    private final static int proxyFee = 2;
    private int totalProxyFee = 0;
    private TrainSaleWindow trainSaleWindow = new TrainSaleWindow();

    @Override
    public void saleTicket(int buyNumber) throws Exception {
        if (buyNumber <= 0) {
            throw new Exception("购买的车票数量必须大于0");
        }
        totalProxyFee = buyNumber * proxyFee;
        log.info("购票手续费："+totalProxyFee);
        trainSaleWindow.saleTicket(buyNumber);
        log.info("实际购票费用："+trainSaleWindow.returnTotalTicketPrice());
    }

    @Override
    public int returnTotalTicketPrice() {
        return totalProxyFee + trainSaleWindow.returnTotalTicketPrice();
    }
}
