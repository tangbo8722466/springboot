package com.springboot.repository.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by tangbo on 2018/1/30 0030.
 */
@Entity(name = "user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity implements Serializable{
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "account", length = 16, nullable = false)
    private String account;

    @Column(name = "user_name", length = 32, nullable = false)
    private String userName;

    @Column(name = "password", length = 16, nullable = false)
    private String password;

    @Column(name = "perms", length = 16, nullable = false)
    private String perms;

    @Column(name = "remark", length = 256)
    private String remark;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

}
