package com.springboot.service.impl;

import com.springboot.Utils.RestResult;
import com.springboot.repository.Dao.HelloDao;
import com.springboot.repository.HelloSpecification;
import com.springboot.repository.entity.HelloEntity;
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
    HelloDao helloDao;

    @Override
    public RestResult<HelloEntity> save(HelloEntity hello) {
        HelloEntity result = (HelloEntity) helloDao.save(hello);
        return new RestResult(RestResult.SUCCESS, null, result);
    }

    @Override
    public RestResult<HelloEntity> update(HelloEntity hello) {
        HelloEntity result = (HelloEntity) helloDao.saveAndFlush(hello);
        return new RestResult(RestResult.SUCCESS, null, result);
    }

    @Override
    public RestResult<HelloEntity> getById(Integer id) {
        HelloEntity result = (HelloEntity) helloDao.findOne(id);
        return new RestResult(RestResult.SUCCESS, null, result);
    }

    @Override
    public RestResult<List<HelloEntity>> list() {
        List<HelloEntity> result = helloDao.findAll();
        return new RestResult(RestResult.SUCCESS, null, result);
    }

    @Override
    public RestResult delete(Integer id) {
        helloDao.delete(id);
        return new RestResult(RestResult.SUCCESS, null);
    }

    @Override
    public RestResult<List<HelloEntity>> page(int start, int limit, String name) {
        List<HelloEntity> list = new ArrayList<HelloEntity>();
        HelloEntity hello = new HelloEntity();
        if(!StringUtils.isEmpty(name)){
            hello.setName(name);
        }
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, limit, sort);
        Page<HelloEntity> page = helloDao.findAll(new HelloSpecification(hello), pageable);
        Iterator<HelloEntity> iterable = page.iterator();
        while(iterable.hasNext()){
            list.add(iterable.next());
        }
        return new RestResult(RestResult.SUCCESS, null, list);
    }
}
