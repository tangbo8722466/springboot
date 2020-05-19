package com.springboot.design.Bridge;

import com.springboot.design.proxy.TrainSaleWindow;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @ClassName CustomerTest
 * @Author sangfor for tangbo
 * @Description //TODO
 * @Date 2020/5/19 14:22
 * @Version 1.0.0
 **/
@Slf4j
public class CustomerTest {
    @Test
    public void test(){
        SaleAbstraction saleAbstraction = new SaleRefinedAbstraction(new TrainSaleWindow());
        saleAbstraction.saleTicket(1);
        log.info("费用："+saleAbstraction.returnTicketFee());
    }
}
