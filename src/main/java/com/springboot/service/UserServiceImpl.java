package com.springboot.service;

import com.springboot.Utils.PageInfo;
import com.springboot.Utils.RestResult;
import com.springboot.constant.RestResultCodeEnum;
import com.springboot.repository.entity.UserEntity;
import com.springboot.service.biz.UserBiz;
import com.springboot.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by tangbo on 2018/1/30 0030.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserBiz userBiz;


    @Override
    public RestResult<UserEntity> save(UserEntity hello) {
        UserEntity result = userBiz.save(hello);
        return new RestResult(RestResultCodeEnum.SUCCESS.code(), null, result);
    }

    @Override
    public RestResult<UserEntity> update(UserEntity hello) {
        UserEntity result = userBiz.update(hello);
        return new RestResult(RestResultCodeEnum.SUCCESS.code(), null, result);
    }

    @Override
    public RestResult<UserEntity> getById(Long id) {
        UserEntity result = userBiz.findById(id);
        return new RestResult(RestResultCodeEnum.SUCCESS.code(), null, result);
    }

    @Override
    public RestResult<UserEntity> findOneByAccount(String account) {
        UserEntity result = userBiz.findOneByAccount(account);
        return new RestResult(RestResultCodeEnum.SUCCESS.code(), null, result);
    }

    @Override
    public RestResult<List<UserEntity>> list() {
        List<UserEntity> result = userBiz.list();
        return new RestResult(RestResultCodeEnum.SUCCESS.code(), null, result);
    }

    @Override
    public RestResult delete(Long id) {
        userBiz.delete(id);
        return new RestResult(RestResultCodeEnum.SUCCESS.code(), null);
    }

    @Override
    public RestResult<List<UserEntity>> page(Integer pageNumber, Integer pageSize, String name) {
        PageInfo pageInfo = PageInfo.builder().pageNumber(pageNumber).pageSize(pageSize).build();
        List<UserEntity> list = userBiz.page(pageInfo, name);
        return new RestResult(RestResultCodeEnum.SUCCESS.code(), null, pageInfo, list);
    }
}
