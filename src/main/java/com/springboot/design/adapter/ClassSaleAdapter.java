package com.springboot.design.adapter;

import com.springboot.design.proxy.TicketProxy;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName ClassSaleAdapter
 * @Author sangfor for tangbo
 * @Description class适配器
 * @Date 2020/5/19 11:06
 * @Version 1.0.0
 **/
@Slf4j
public class ClassSaleAdapter extends TicketProxy implements ISale {
    /**
     * 通过继承，继承class属性，实现适配
     * @return
     * @throws Exception
     */
    @Override
    public int saleTicket(){
        log.info("进入Class适配器");
        try {
            super.saleTicket(1);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return super.returnTotalTicketPrice();
    }
}
