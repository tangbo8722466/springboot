package com.springboot.Vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by tangbo on 2018/10/24 0024.
 */
@Data
public class HelloCreateVo {
    @NotNull(message = "name can not be empty!")
    private String name;
    @NotNull(message = "remark can not be empty!")
    private String remark;
}
