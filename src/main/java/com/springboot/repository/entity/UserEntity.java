package com.springboot.repository.entity;

import lombok.*;

import javax.persistence.*;

/**
 * Created by tangbo on 2018/1/30 0030.
 */
@Entity(name = "user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "user_name", length = 32, nullable = false)
    private String userName;

    @Column(name = "account", length = 16, nullable = false)
    private String account;

    @Column(name = "password", length = 16, nullable = false)
    private String password;

    @Column(name = "remark", length = 256)
    private String remark;
}
