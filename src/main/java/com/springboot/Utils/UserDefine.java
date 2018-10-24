package com.springboot.Utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by tangbo on 2018/3/13 0013.
 */
@Component
@PropertySource("classpath:config/userdefine.properties")
@ConfigurationProperties(prefix = "userdefine")
public class UserDefine {
    private String name;
    private String passwd;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}
