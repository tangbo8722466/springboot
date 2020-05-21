package com.springboot.Vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @ClassName DataEncryptVo
 * @Author sangfor for tangbo
 * @Description //TODO
 * @Date 2020/5/21 15:17
 * @Version 1.0.0
 **/
@Data
@Builder
@ApiModel(value = "加密请求结构体")
public class DataEncryptVo {
    @ApiModelProperty(value = "被RSA加密的[AES加密key]")
    private String skey;

    @ApiModelProperty(value = "被AES加密后的JSON内容")
    private String body;
}
