package com.springboot.design.decorate;

import com.springboot.design.proxy.ITrainStation;

/**
 * @ClassName TicketAbstractDecorate
 * @Author sangfor for tangbo
 * @Description 不影响售卖火车票的前提下，出售保险
 * @Date 2020/5/25 9:51
 * @Version 1.0.0
 **/
public class TicketDecorate extends TicketAbstractDecorate {

    //保险费
    private int insuranceFee = 1;

    private int insuranceTotalFee = 0;

    public TicketDecorate(ITrainStation trainStation) {
        super(trainStation);
    }

    @Override
    public void saleTicket(int buyNumber) throws Exception {
        super.saleTicket(buyNumber);
        saleInsurance(buyNumber);
    }

    @Override
    public int returnTotalTicketPrice() {
        return super.returnTotalTicketPrice() + insuranceTotalFee;
    }

    public void saleInsurance(int number){
        insuranceTotalFee = number * insuranceFee;
    }
}
