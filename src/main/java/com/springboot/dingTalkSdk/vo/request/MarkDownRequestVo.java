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
@ApiModel(value = "MarkDown消息")
public class MarkDownRequestVo implements Serializable {
    private static final long serialVersionUID = 7971707849482923417L;
    @ApiModelProperty(value = "消息标题", example = "杭州天气")
    @NotNull(message = "title can not be empty")
    private String title;
    @ApiModelProperty(value = "内容", example = "#### 杭州天气 @156xxxx8827\\n\" +\n" +
            "                 \"> 9度，西北风1级，空气良89，相对温度73%\\n\\n\" +\n" +
            "                 \"> ![screenshot](https://gw.alicdn.com/tfs/TB1ut3xxbsrBKNjSZFpXXcXhFXa-846-786.png)\\n\"  +\n" +
            "                 \"> ###### 10点20分发布 [天气](http://www.thinkpage.cn/) \\n")
    @NotNull(message = "content can not be empty")
    @Length(min = 1, max = 1024, message = "content size limit 1 to 1024")
    private String content;
    @ApiModelProperty(value = "@相关人", example = "[\"15196635394\"]")
    private List<String> atUsers;
    @ApiModelProperty(value = "是否@所以人", example = "false")
    private Boolean isAtAll;
}
