package com.springboot.design.proxy;

/**
 * @ClassName ITrainStation
 * @Author sangfor for tangbo
 * @Description //TODO
 * @Date 2020/5/15 15:20
 * @Version 1.0.0
 **/
public interface ITrainStation {
    void saleTicket(int buyNumber) throws Exception;

    int returnTotalTicketPrice();
}
