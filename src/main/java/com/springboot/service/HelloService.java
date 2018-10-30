package com.springboot.service;

import com.springboot.Utils.RestResult;
import com.springboot.repository.entity.UserEntity;

import java.util.List;

/**
 * Created by tangbo on 2018/1/30 0030.
 */
public interface HelloService {
    RestResult<UserEntity> save(UserEntity hello);
    RestResult<UserEntity> update(UserEntity hello);
    RestResult<UserEntity> getById(Integer id);
    RestResult<List<UserEntity>> list();
    RestResult delete(Integer id);
    RestResult<List<UserEntity>> page(int start, int limit, String name);
}
