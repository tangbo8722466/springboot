package com.springboot.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public abstract class ObjectRedisService<T> {

    /**
     * 实例化 RedisTemplate对象
     */
    @Autowired
    protected RedisTemplate<String, Object> redisTemplate;

    /**
     * 定义Hash结构 操作存储实体对象
     */
    @Resource
    protected ValueOperations<String, T> valueOperations;

    /**
     * 定义Hash表的redis key名称
     *
     * @return
     */
    protected abstract String getRedisKey();

    /**
     * 添加键值对 key:Object(doamin)
     *
     * @param key    key
     * @param domain 对象
     * @param expire 过期时间(单位:秒),传入 -1 时表示不设置过期时间
     */
    public void set(String key, T domain, long expire) {
        if (expire == -1){
            valueOperations.set(getRedisKey() + key, domain);
            return;
        }
        valueOperations.set(getRedisKey() + key, domain, expire, TimeUnit.SECONDS);
    }



    /**
     * 删除key名称的元素
     *
     * @param key 传入key的名称
     */
    public void remove(String key) {
        redisTemplate.delete(getRedisKey() + key);
    }

    /**
     * 查询key名称的元素
     *
     * @param key 查询的key
     * @return
     */
    public T get(String key) {
        return valueOperations.get(getRedisKey() + key);
    }

    /**
     * 判断在相应Hash表下key是否存在
     *
     * @param key 传入key的名称
     * @return
     */
    public boolean isKeyExists(String key) {
        return redisTemplate.hasKey(getRedisKey() + key);
    }


}