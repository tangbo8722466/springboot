package com.springboot.Vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @ClassName DataEncryptVo
 * @Author sangfor for tangbo
 * @Description //TODO
 * @Date 2020/5/21 15:17
 * @Version 1.0.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "加密请求结构体")
public class DataEncryptVo {
    @ApiModelProperty(value = "被RSA加密的[AES加密key]")
    @NotNull
    private String skey;

    @ApiModelProperty(value = "被AES加密后的JSON内容")
    @NotNull
    private String body;

    @ApiModelProperty(value = "解密后的JSON内容", hidden = true)
    private String data;

}
