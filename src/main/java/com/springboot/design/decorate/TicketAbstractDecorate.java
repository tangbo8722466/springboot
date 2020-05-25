package com.springboot.design.decorate;

import com.springboot.design.proxy.TrainStation;

/**
 * @ClassName TicketAbstractDecorate
 * @Author sangfor for tangbo
 * @Description //TODO
 * @Date 2020/5/25 9:51
 * @Version 1.0.0
 **/
public class TicketAbstractDecorate implements TrainStation {
    private TrainStation trainStation;

    public TicketAbstractDecorate(TrainStation trainStation) {
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
