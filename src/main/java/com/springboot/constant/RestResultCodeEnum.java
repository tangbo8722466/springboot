package com.springboot.constant;

public enum RestResultCodeEnum {
    SUCCESS(0, "success"),
    FAIL(1, "fail"),
    EXCEPTION(500, "exception");

    private int code;

    private String msg;

    RestResultCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int code() {
        return this.code;
    }

    public String msg(){
        return this.msg;
    }
}
