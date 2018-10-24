package com.springboot.service;

import com.springboot.Utils.RestResult;
import com.springboot.repository.entity.HelloEntity;

import java.util.List;

/**
 * Created by tangbo on 2018/1/30 0030.
 */
public interface HelloService {
    RestResult<HelloEntity> save(HelloEntity hello);
    RestResult<HelloEntity> update(HelloEntity hello);
    RestResult<HelloEntity> getById(Integer id);
    RestResult<List<HelloEntity>> list();
    RestResult delete(Integer id);
    RestResult<List<HelloEntity>> page(int start, int limit, String name);
}
