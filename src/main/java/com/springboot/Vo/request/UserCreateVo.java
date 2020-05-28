package com.springboot.Vo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * Created by tangbo on 2018/10/24 0024.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateVo {
    @NotNull(message = "userName can not be empty!")
    @Length(min = 1, max = 32, message = "userName size limits from 1 to 32")
    private String userName;

    @NotNull(message = "account can not be empty!")
    @Length(min = 8, max = 16, message = "account size limits from 8 to 16")
    private String account;

    @NotNull(message = "password can not be empty!")
    @Length(min = 1, max = 16, message = "password size limits from 1 to 16")
    private String password;

    @NotNull(message = "remark can not be empty!")
    @Length(max = 256, message = "remark size limits 256 ")
    private String remark;

    @NotNull(message = "perms can not be empty!")
    @Length(max = 256, message = "perms size limits 256 ")
    private String perms;
}
