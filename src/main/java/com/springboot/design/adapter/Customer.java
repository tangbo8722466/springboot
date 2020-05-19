package com.springboot.design.adapter;

import com.springboot.design.proxy.TicketProxy;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @ClassName Customer
 * @Author sangfor for tangbo
 * @Description //TODO
 * @Date 2020/5/19 11:22
 * @Version 1.0.0
 **/
@Slf4j
public class Customer {
    @Test
    public void classAdapter(){
        ClassSaleAdapter adapter = new ClassSaleAdapter();
        log.info("购买一张花费："+adapter.saleTicket());
    }

    @Test
    public void objectAdapter(){
        ObjectSaleAdapter adapter = new ObjectSaleAdapter(new TicketProxy());
        log.info("购买一张花费："+adapter.saleTicket());
    }

}
