package com.springboot.service;

import com.springboot.redis.RedisService;
import com.springboot.repository.entity.UserEntity;
import org.springframework.stereotype.Service;

/**
 * 用户redis service继承类
 * @author zxy
 *
 */
@Service("userRedisService")
public class UserRedisServiceImpl extends RedisService<UserEntity> {

    //自定义redis key作为Hash表的key名称
    private static final String REDIS_KEY = "USER_KEY";

    @Override
    protected String getRedisKey() {
        // TODO Auto-generated method stub
        return REDIS_KEY;
    }

}