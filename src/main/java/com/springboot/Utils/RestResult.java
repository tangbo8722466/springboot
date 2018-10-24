package com.springboot.Utils;

/**
 * Created by tangbo on 2018/1/30 0030.
 */
public class RestResult<T> {
    public static final int SUCCESS = 200;
    public static final int FAIL = -1;
    int code;

    String errorMsg;

    T data;

    public RestResult() {
    }

    public RestResult(int code, String errorMsg) {
        this.code = code;
        this.errorMsg = errorMsg;
    }

    public RestResult(int code, String errorMsg, T data) {
        this.code = code;
        this.errorMsg = errorMsg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
