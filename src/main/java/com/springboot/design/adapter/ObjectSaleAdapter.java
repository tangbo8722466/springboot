package com.springboot.design.adapter;

import com.springboot.design.proxy.TrainStation;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName ObjectSaleAdapter
 * @Author sangfor for tangbo
 * @Description 对象适配器
 * @Date 2020/5/19 11:13
 * @Version 1.0.0
 **/
@Slf4j
public class ObjectSaleAdapter implements ISale{
    private TrainStation target;

    /**
     * 通过构造函数传入适配对象，实现适配
     * @param target
     */
    public ObjectSaleAdapter(TrainStation target) {
        log.info("进入对象适配器");
        this.target = target;
    }

    @Override
    public int saleTicket(){
        try {
            target.saleTicket(1);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return target.returnTotalTicketPrice();
    }
}
