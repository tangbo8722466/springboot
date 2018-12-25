package com.springboot.Utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by tangbo on 2018/1/30 0030.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestResult<T> implements Serializable{
    private String status;
    private String errorMsg;
    private PageInfo pageInfo;
    private T data;

    public RestResult(String status, String errorMsg) {
        this.status = status;
        this.errorMsg = errorMsg;
    }

    public RestResult(String status, String errorMsg, T data) {
        this.status = status;
        this.errorMsg = errorMsg;
        this.data = data;
    }
}
