package com.springboot.kafka.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Auther: hs
 * @Date: 2019/2/23 17:53
 * @Description:
 */
@Data
public class User {
    private Integer id;
    private String userName;
    private String description;
    //@JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}