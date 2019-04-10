package com.springboot.Utils;

/**
 * Created by tangbo on 2019/4/10 0010.
 */
public enum QuenceEnum {
    HELLO("hello");

    private String quence;

    QuenceEnum(String quence) {
        this.quence = quence;
    }

    public String value() {
        return this.quence;
    }
}
