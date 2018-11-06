package com.springboot.Utils;

public enum RestResultStatusEnum {
    SUCCESS("ok"),
    FAIL("fail"),
    EXCEPTION("exception");

    private String status;

    RestResultStatusEnum(String status) {
        this.status = status;
    }

    public String value() {
        return this.status;
    }
}
