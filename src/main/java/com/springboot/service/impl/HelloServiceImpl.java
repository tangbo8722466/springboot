package com.springboot.service.impl;

import com.springboot.Utils.RestResult;
import com.springboot.repository.Dao.UserDao;
import com.springboot.repository.UserSpecification;
import com.springboot.repository.entity.UserEntity;
import com.springboot.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by tangbo on 2018/1/30 0030.
 */
@Service("helloService")
@Transactional
public class HelloServiceImpl implements HelloService{
    @Autowired
    UserDao helloDao;

    @Override
    public RestResult<UserEntity> save(UserEntity hello) {
        UserEntity result = (UserEntity) helloDao.save(hello);
        return new RestResult(RestResult.SUCCESS, null, result);
    }

    @Override
    public RestResult<UserEntity> update(UserEntity hello) {
        UserEntity result = (UserEntity) helloDao.saveAndFlush(hello);
        return new RestResult(RestResult.SUCCESS, null, result);
    }

    @Override
    public RestResult<UserEntity> getById(Long id) {
        UserEntity result = (UserEntity) helloDao.findOne(id);
        return new RestResult(RestResult.SUCCESS, null, result);
    }

    @Override
    public RestResult<List<UserEntity>> list() {
        List<UserEntity> result = helloDao.findAll();
        return new RestResult(RestResult.SUCCESS, null, result);
    }

    @Override
    public RestResult delete(Long id) {
        helloDao.delete(id);
        return new RestResult(RestResult.SUCCESS, null);
    }

    @Override
    public RestResult<List<UserEntity>> page(int start, int limit, String name) {
        List<UserEntity> list = new ArrayList<UserEntity>();
        UserEntity userEntity = new UserEntity();
        if(!StringUtils.isEmpty(name)){
            userEntity.setUserName(name);
        }
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start - 1, limit, sort);
        Page<UserEntity> page = helloDao.findAll(new UserSpecification(userEntity), pageable);
        Iterator<UserEntity> iterable = page.iterator();
        while(iterable.hasNext()){
            list.add(iterable.next());
        }
        return new RestResult(RestResult.SUCCESS, null, list);
    }
}
