package com.springboot.Vo.request;

import lombok.Data;

/**
 * Created by tangbo on 2018/10/24 0024.
 */
@Data
public class UserUpdateVo {
    private String userName;
    private String password;
    private String remark;
}
