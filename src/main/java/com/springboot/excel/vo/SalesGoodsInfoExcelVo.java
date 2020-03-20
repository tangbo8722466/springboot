package com.springboot.excel.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.springboot.excel.PurchasePlace;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * excel 生鲜商品
 *
 * @author appple
 */
@Getter
@Setter
@NoArgsConstructor
public class SalesGoodsInfoExcelVo extends BaseRowModel implements Serializable {

    /**
     * 序列化ID
     */
    private static final long serialVersionUID = -6879864846926811945L;

    /**
     * 商品seq
     */
    @ExcelIgnore
    @ApiModelProperty(value = "商品seq")
    private Integer goodsSeq;

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    @ExcelProperty(index = 0, value = "商品名称(必填)")
    @NotBlank(message = "商品名称不能为空")
    private String goodsName;

    /**
     * 销量
     */
    @ApiModelProperty(value = "销量")
    @ExcelProperty(index = 1, value = "销量（选填）")
    @Pattern(regexp = "^([1-9]\\d*)|[0]", message = "销量必须为整数")
    private String saledNum;

    //产品参数
    /**
     * 最小净含量
     */
    @ApiModelProperty(value = "最小净含量")
    @ExcelProperty(index = 2, value = "最小净含量[KG](必填)")
    @NotBlank(message = "最小净含量不能为空")
    @Pattern(regexp = "^(([1-9]\\d*)|[0])(\\.\\d{0,2})?$", message = "最小净含量只支持保留两位小数")
    private String netWeightMin;

    /**
     * 最大净含量
     */
    @ApiModelProperty(value = "最大净含量")
    @ExcelProperty(index = 3, value = "最大净含量[KG](必填)")
    @NotBlank(message = "最大净含量不能为空")
    @Pattern(regexp = "^(([1-9]\\d*)|[0])(\\.\\d{0,2})?$", message = "最大净含量只支持保留两位小数")
    private String netWeightMax;

    /**
     * 采购地:国内/国外
     */
    @ApiModelProperty(value = "采购地: 国内/国外")
    @ExcelProperty(index = 4, value = "采购地: 国内/国外")
    @NotBlank(message = "采购地不能为空")
    @PurchasePlace
    private String purchasePlace;

    /**
     * 抢购价
     */
    @ApiModelProperty(value = "抢购价")
    @ExcelProperty(index = 5, value = "抢购价(元)")
    @NotBlank(message = "抢购价不能为空")
    @Pattern(regexp = "^(([1-9]\\d*)|[0])(\\.\\d{0,2})?$", message = "抢购价只支持保留两位小数")
    private String buyPrice;

    /**
     * 价格
     */
    @ApiModelProperty(value = "原价")
    @ExcelProperty(index = 6, value = "原价(元)")
    @NotBlank(message = "原价不能为空")
    @Pattern(regexp = "^(([1-9]\\d*)|[0])(\\.\\d{0,2})?$", message = "原价只支持保留两位小数")
    private String price;

    /**
     * 分拣费
     */
    @ApiModelProperty(value = "分拣费")
    @ExcelProperty(index = 7, value = "分拣费(元)")
    @NotBlank(message = "分拣费不能为空")
    @Pattern(regexp = "^(([1-9]\\d*)|[0])(\\.\\d{0,2})?$", message = "发放金额只支持保留两位小数")
    private String sortingFee;

    /**
     * 总库存
     */
    @ApiModelProperty(value = "总库存")
    @ExcelProperty(index = 8, value = "总库存")
    @NotBlank(message = "总库存不能为空")
    @Pattern(regexp = "^([1-9]\\d*)|[0]", message = "总库存只支持整数")
    private String quantity;


    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    @ExcelProperty(index = 9, value = "联系电话(必填)")
    @NotBlank(message = "联系电话不能为空")
    @Pattern(regexp = "^[1](([3][0-9])|([4][5-9])|([5][0-3,5-9])|([6][5,6])|([7][0-8])|([8][0-9])|([9][1,8,9]))[0-9]{8}$", message = "手机号格式不正确")
    private String merPhone;
}
