package com.springboot.Utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("分页结构体")
public class PageInfo<T> implements Serializable{
    @ApiModelProperty(name = "当前页数")
    private Integer pageNumber;
    @ApiModelProperty(name = "每页条数")
    private Integer pageSize;
    @ApiModelProperty(name = "总数")
    private Long total;
    @ApiModelProperty(name = "数据列表")
    private List<T> items;
}
