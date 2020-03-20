package com.springboot.service.impl;

import com.springboot.Utils.RestResult;
import com.springboot.repository.entity.UserEntity;

import java.util.List;

/**
 * Created by tangbo on 2018/1/30 0030.
 */
public interface UserService {
    RestResult<UserEntity> save(UserEntity hello);
    RestResult<UserEntity> update(UserEntity hello);
    RestResult<UserEntity> getById(Long id);
    RestResult<UserEntity> findOneByAccount(String account);
    RestResult<List<UserEntity>> list();
    RestResult delete(Long id);
    RestResult<List<UserEntity>> page(Integer pageNumber, Integer pageSize, String name);
    default boolean health(){
        return true;
    }
}
