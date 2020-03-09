package com.springboot.excel.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.List;

/**
 * excel 生鲜商品错误提示
 *
 * @author tangbo
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExcelErrorVo implements Serializable {


    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 2085745825187944069L;

    /**
     * 表格行数
     */
    @ApiModelProperty(value = "表格行数")
    private Integer lineNum;

    /**
     * 错误信息
     */
    @ApiModelProperty(value = "错误信息")
    private String errorMsg;

    public static String toString(List<ExcelErrorVo> list) {
        StringBuffer buffer = new StringBuffer();
        if (CollectionUtils.isEmpty(list)){
            return "";
        }
        list.stream().forEach(v ->buffer.append("表格第"+v.getLineNum()+"行数据错误，错误原因："+v.getErrorMsg()+"；"));
        return buffer.toString();
    }
}
