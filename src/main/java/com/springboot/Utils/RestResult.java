package com.springboot.Utils;

import com.springboot.Vo.response.Empty;
import com.springboot.constant.RestResultCodeEnum;
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
    private static final long serialVersionUID = 1794095672411145784L;
    private int code;
    private String errorMsg;
    private T data;

    public boolean isSuccess(){
        return RestResultCodeEnum.SUCCESS.code() == code;
    }

    public RestResult(int code, String errorMsg) {
        this.code = code;
        this.errorMsg = errorMsg;
    }

    public static <T> RestResult<T> buildSuccessResponse(){
        return new RestResult(RestResultCodeEnum.SUCCESS.code(),RestResultCodeEnum.SUCCESS.msg(),  new Empty());
    }

    public static <T> RestResult<T> buildSuccessResponse(T data){
        return new RestResult<>(RestResultCodeEnum.SUCCESS.code(),RestResultCodeEnum.SUCCESS.msg(), data);
    }

    public static <T> RestResult<T> buildFailResponse(){
        return new RestResult<>(RestResultCodeEnum.FAIL.code(),RestResultCodeEnum.FAIL.msg());
    }

    public static <T> RestResult<T> buildFailResponse(String errorMsg){
        return new RestResult<>(RestResultCodeEnum.FAIL.code(), errorMsg);
    }

    public static <T> RestResult<T> buildFailResponse(int code, String errorMsg){
        return new RestResult<>(code, errorMsg);
    }

    public static <T> RestResult<T> buildFailResponse(int code, String errorMsg, T data){
        return new RestResult<>(code, errorMsg, data);
    }
}
