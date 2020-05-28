package com.springboot.service;

import com.springboot.redis.HashRedisService;
import com.springboot.redis.ObjectRedisService;
import com.springboot.repository.entity.UserEntity;
import org.springframework.stereotype.Service;

/**
 * 用户redis service继承类
 * @author zxy
 *
 */
@Service(value = "encryptObjectRedisService")
public class EncryptObjectRedisService extends ObjectRedisService<String> {

    //自定义redis key作为key名称
    private static final String REDIS_KEY = "RAS_ENCRYPT";

    @Override
    protected String getRedisKey() {
        // TODO Auto-generated method stub
        return REDIS_KEY;
    }

}