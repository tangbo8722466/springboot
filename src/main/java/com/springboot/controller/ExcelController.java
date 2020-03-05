package com.springboot.controller;

import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSONObject;
import com.springboot.Vo.response.PurchaseSalesOrderResponse;
import com.springboot.excel.EasyExcelUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * https://alibaba-easyexcel.github.io/quickstart/write.html
 */
@Controller
@RequestMapping("/v1/")
@Api(value = "excel采购表")
public class ExcelController {

    @ApiOperation("excel采购表")
    @PostMapping("/exportPurchaseOrders")
    public void exportOrders(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject requestVo) throws IOException {
        int row = 0;
        List<PurchaseSalesOrderResponse> list = new ArrayList<>();
        for (int i=0; i<10; i++) {
            list.add(PurchaseSalesOrderResponse.builder().indexNo(row++).goodsName("测试商品"+ (row++)).units("份").goodsCount(row++).build());
        }
        String fileName = "采购表";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderNo", "2020");
        map.put("remark", "采购单");
        map.put("companyName", "测试");
        EasyExcelUtils.fillExcelStreamMutilByEaysExcel(response, list, map, ExcelTypeEnum.XLSX, fileName);
    }
}
