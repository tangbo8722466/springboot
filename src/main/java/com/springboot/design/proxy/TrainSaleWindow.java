package com.springboot.design.proxy;

/**
 * @ClassName TrainSaleWindow
 * @Author sangfor for tangbo
 * @Description //TODO
 * @Date 2020/5/15 15:21
 * @Version 1.0.0
 **/
public class TrainSaleWindow implements ITrainStation {

    private final static int ticketPrice = 5;

    private int totalTicketPrice = 0;

    @Override
    public void saleTicket(int buyNumber) {
        if (buyNumber <= 0) {
            totalTicketPrice = 0;
        }
        totalTicketPrice = ticketPrice * buyNumber;
    }

    @Override
    public int returnTotalTicketPrice() {
        return totalTicketPrice;
    }
}
