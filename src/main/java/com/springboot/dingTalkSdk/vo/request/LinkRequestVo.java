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
@ApiModel(value = "链接消息")
public class LinkRequestVo implements Serializable {
    private static final long serialVersionUID = -2854441161448570863L;
    @ApiModelProperty(value = "消息跳转地址", example = "https://www.dingtalk.com/s?__biz=MzA4NjMwMTA2Ng==&mid=26503168" +
            "42&idx=1&sn=60da3ea2b29f1dcc43a7c8e4a7c97a16&scene=2&srcid=09189AnRJEdIiWVaKltFzNTw&from=timeline&isappinstalled" +
            "=0&key=&ascene=2&uin=&devicetype=android-23&version=26031933&nettype=WIFI")
    @NotNull(message = "messageUrl can not be empty")
    private String messageUrl;
    @ApiModelProperty(value = "消息图片", example = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec" +
            "=1568634161499&di=e8816f837e025155085dcb109ba2dd84&imgtype=0&src=http%3A%2F%2Fb.hiphotos.baidu.com%2Fimage%2" +
            "Fpic%2Fitem%2F908fa0ec08fa513db777cf78376d55fbb3fbd9b3.jpg")
    //@NotNull(message = "picUrl can not be empty")
    private String picUrl;
    @ApiModelProperty(value = "消息标题", example = "时代的火车向前开")
    @NotNull(message = "title can not be empty")
    private String title;
    @ApiModelProperty(value = "内容", example = "这个即将发布的新版本，创始人陈航（花名“无招”）称它为“红树林”。\n" +
            "而在此之前，每当面临重大升级，产品经理们都会取一个应景的代号，这一次，为什么是“红树林”？")
    @NotNull(message = "content can not be empty")
    @Length(min = 1, max = 1024, message = "content size limit 1 to 1024")
    private String content;
    @ApiModelProperty(value = "@相关人", example = "[\"15196635394\"]")
    private List<String> atUsers;
    @ApiModelProperty(value = "是否@所以人", example = "false")
    private Boolean isAtAll;
}
