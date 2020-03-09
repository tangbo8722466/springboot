package com.springboot.controller;

import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSONObject;
import com.springboot.Utils.RestResult;
import com.springboot.Vo.response.PurchaseSalesOrderResponse;
import com.springboot.excel.EasyExcelUtils;
import com.springboot.excel.ExcelUtil;
import com.springboot.excel.ValidatorUtil;
import com.springboot.excel.vo.ExcelErrorVo;
import com.springboot.excel.vo.SalesGoodsInfoExcelVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * https://alibaba-easyexcel.github.io/quickstart/write.html
 */
@Controller
@RequestMapping("/v1/")
@Api(value = "excel采购表")
@Slf4j
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

    /**
     * 商品excel导入模板下载
     *
     * @param
     * @return
     */
    @GetMapping("/excel/template/export")
    @ResponseBody
    public void export(HttpServletRequest request, HttpServletResponse response) {
        try {
            String fileName = "商品信息批量上传模板.xlsx";
            response.setContentType("multipart/form-data");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ExcelTypeEnum.XLSX);
            ServletOutputStream out = response.getOutputStream();
            InputStream in = new ClassPathResource("template/goodsTemplate.xlsx").getInputStream();
            int len = 0;
            byte[] buffer = new byte[in.available()];
            while ((len = in.read(buffer, 0, buffer.length)) != -1){
                out.write(buffer, 0, len);
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 商品excel导入发放
     *
     * @param multipartFile
     * @return
     */
    @PostMapping("/excel/import")
    @ResponseBody
    public RestResult<String> importExcel(MultipartFile multipartFile) {
        if (multipartFile == null) {
            return RestResult.buildFailResponse("请导入excel");
        }
        if (!multipartFile.getOriginalFilename().endsWith(".xlsx")) {
            return RestResult.buildFailResponse("只能上传xlsx文件");
        }
        try {
            List<ExcelErrorVo> excelErrors = new ArrayList<>();
            List<SalesGoodsInfoExcelVo> list = ExcelUtil.readExcel(multipartFile, SalesGoodsInfoExcelVo.class, 1, 1);
            if (CollectionUtils.isEmpty(list)) {
                return RestResult.buildFailResponse("请勿上传空表格");
            }
            //数据校验
            for (int i = 0; i < list.size(); i++){
                SalesGoodsInfoExcelVo excelVo = list.get(i);
                Set<ConstraintViolation<SalesGoodsInfoExcelVo>> validate = ValidatorUtil.createValidator().validate(excelVo);
                String error = ValidatorUtil.error(validate);
                if (StringUtils.isNotEmpty(error)) {
                    //排除表头
                    excelErrors.add(ExcelErrorVo.builder().lineNum(i+2).errorMsg(error).build());
                }
            }

            if (!CollectionUtils.isEmpty(excelErrors)) {
                //保存错误信息
                return RestResult.buildFailResponse("上传表格格式有误，"+ ExcelErrorVo.toString(excelErrors));
            }
            return RestResult.buildSuccessResponse("数据导入成功");
        } catch (Exception e) {
            log.error("商品excel导入发放出错", e);
            return RestResult.buildFailResponse("数据导入失败, "+e.getMessage());
        }
    }
}
