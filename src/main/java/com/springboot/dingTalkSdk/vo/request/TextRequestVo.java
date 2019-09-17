package com.springboot.dingTalkSdk.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "文本消息")
public class TextRequestVo implements Serializable {
    private static final long serialVersionUID = -2532774329451420652L;
    @ApiModelProperty(value = "内容", example = "测试消息")
    @NotNull(message = "content can not be empty")
    @Length(min = 1, max = 1024, message = "content size limit 1 to 1024")
    private String content;
    @ApiModelProperty(value = "@相关人", example = "[\"15196635394\"]")
    private List<String> atUsers;
    @ApiModelProperty(value = "是否@所以人", example = "false")
    private Boolean isAtAll;
}
