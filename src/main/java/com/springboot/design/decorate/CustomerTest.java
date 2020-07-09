package com.springboot.design.decorate;

import com.springboot.design.proxy.ITrainStation;
import com.springboot.design.proxy.TrainSaleWindow;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @ClassName CustomerTest
 * @Author sangfor for tangbo
 * @Description //TODO
 * @Date 2020/5/25 10:02
 * @Version 1.0.0
 **/
@Slf4j
public class CustomerTest {
    @Test
    public void buyTicket() throws Exception {
        ITrainStation trainStation = new TrainSaleWindow();
        trainStation.saleTicket(1);
        log.info("购买费用："+trainStation.returnTotalTicketPrice());
        ITrainStation trainStationDecorate = new TicketDecorate(trainStation);
        trainStationDecorate.saleTicket(1);
        log.info("购买费用(附加保险)："+trainStationDecorate.returnTotalTicketPrice());
    }
}
