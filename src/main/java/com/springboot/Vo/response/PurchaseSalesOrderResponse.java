package com.springboot.Vo.response;


import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "采购单")
@Builder
public class PurchaseSalesOrderResponse extends BaseRowModel implements Serializable {

    /**
     * 序列化ID
     */
    private static final long serialVersionUID = -5809782578272943999L;


    /**
     * 序号
     */
    @ApiModelProperty("序号")
    @ExcelProperty(value = "序号", index = 0)
    private Integer indexNo;

    /**
     * 商品名称
     */
    @ApiModelProperty("商品名称")
    @ExcelProperty(value = "商品名称", index = 1)
    private String goodsName;


    @ApiModelProperty("单位")
    @ExcelProperty(value = "单位", index = 2)
    private String units = "份";

    /**
     * 采购数量
     */
    @ApiModelProperty("采购数量")
    @ExcelProperty(value = "采购数量", index = 3)
    private Integer goodsCount;

}
