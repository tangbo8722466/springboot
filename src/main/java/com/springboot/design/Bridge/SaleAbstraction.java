package com.springboot.design.Bridge;

import com.springboot.design.proxy.ITrainStation;

/**
 * @ClassName SaleAbstraction
 * @Author sangfor for tangbo
 * @Description //TODO
 * @Date 2020/5/19 14:13
 * @Version 1.0.0
 **/
public abstract class SaleAbstraction {
    public ITrainStation trainStation;
    public SaleAbstraction(ITrainStation trainStation) {
        this.trainStation = trainStation;
    }

    public abstract void saleTicket(int number);

    public abstract int returnTicketFee();
}
