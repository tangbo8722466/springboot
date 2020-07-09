package com.springboot.design.Bridge;

import com.springboot.design.proxy.ITrainStation;

/**
 * @ClassName SaleRefinedAbstraction
 * @Author sangfor for tangbo
 * @Description //TODO
 * @Date 2020/5/19 14:17
 * @Version 1.0.0
 **/
public class SaleRefinedAbstraction extends SaleAbstraction{

    public SaleRefinedAbstraction(ITrainStation trainStation) {
        super(trainStation);
    }

    @Override
    public void saleTicket(int number) {
        try {
            trainStation.saleTicket(number);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int returnTicketFee() {
        return trainStation.returnTotalTicketPrice();
    }
}
