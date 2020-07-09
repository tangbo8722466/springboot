package com.springboot.design.decorate;

import com.springboot.design.proxy.ITrainStation;

/**
 * @ClassName TicketAbstractDecorate
 * @Author sangfor for tangbo
 * @Description //TODO
 * @Date 2020/5/25 9:51
 * @Version 1.0.0
 **/
public class TicketAbstractDecorate implements ITrainStation {
    private ITrainStation trainStation;

    public TicketAbstractDecorate(ITrainStation trainStation) {
        this.trainStation = trainStation;
    }

    @Override
    public void saleTicket(int buyNumber) throws Exception {
        trainStation.saleTicket(buyNumber);
    }

    @Override
    public int returnTotalTicketPrice() {
        return trainStation.returnTotalTicketPrice();
    }
}
