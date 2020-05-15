package com.springboot.design.proxy;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @ClassName Customer
 * @Author sangfor for tangbo
 * @Description //TODO
 * @Date 2020/5/15 15:37
 * @Version 1.0.0
 **/
@Slf4j
public class Customer {
    @Test
    public void buyTicket(){
        TicketProxy ticketProxy = new TicketProxy();
        int buyNumber = 5;
        try {
            log.info("使用中介购票数量："+buyNumber);
            ticketProxy.saleTicket(buyNumber);
            int tickerFee = ticketProxy.returnTotalTicketPrice();
            log.info("中介车票费用："+tickerFee);
        } catch (Exception e) {
            log.info("购买车票失败");
        }

    }
}
