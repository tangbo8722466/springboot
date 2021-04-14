package com.springboot.service.biz;

import com.springboot.Utils.PageInfo;
import com.springboot.constant.RedisHeaderEnum;
import com.springboot.repository.Dao.UserDao;
import com.springboot.repository.UserSpecification;
import com.springboot.repository.entity.UserEntity;
import com.springboot.service.UserHashRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * Created by tangbo on 2018/1/30 0030.
 */
@Component
@Transactional
public class UserBiz{

    final String header = RedisHeaderEnum.USER_CACHE_KEY;

    @Autowired
    UserDao userDao;

    @Autowired
    UserHashRedisService userRedisService;

    @Cacheable(value = header, key = "#hello.id")
    //@Cacheable(value = header, key = "#p0.id")
    public UserEntity save(UserEntity hello) {
        UserEntity result = userDao.save(hello);
        userRedisService.put(header + hello.getId(), hello, -1);
        UserEntity userEntity = userRedisService.get(header + hello.getId());
        return result;
    }
    //allEntries 清空缓存所有属性 确保更新后缓存刷新
    @CacheEvict(value = header, key = "#p0.id", allEntries=true)
    public UserEntity update(UserEntity hello) {
        return userDao.saveAndFlush(hello);
    }

    @Cacheable(value = header, key="#id") //缓存,这里没有指定key.
    public UserEntity findById(Long id) {
        return userDao.getOne(id);
    }

    public UserEntity findOneByAccount(String account) {
        Optional<UserEntity> optionalUserEntity = userDao.findOne(new UserSpecification(UserEntity.builder().account(account).build()));
        return optionalUserEntity.get();
    }

    public List<UserEntity> list() {
        return userDao.findAll();
    }


    @CacheEvict(value = header, key="#id", allEntries=true)
    public void delete(Long id) {
        userDao.deleteById(id);
    }

    @Cacheable(value = header, key="#p0.pageNumber-#p0.pageSize-#p1")
    public PageInfo<UserEntity> page(PageInfo pageInfo, String userName) {
        List<UserEntity> list = new ArrayList<UserEntity>();
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(userName);
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(pageInfo.getPageNumber() - 1, pageInfo.getPageSize(), sort);
        Page<UserEntity> page = userDao.findAll(new UserSpecification(userEntity), pageable);
        pageInfo.setTotal(page.getTotalElements());
        Iterator<UserEntity> iterable = page.iterator();
        while(iterable.hasNext()){
            list.add(iterable.next());
        }
        pageInfo.setItems(list);
        return pageInfo;
    }
}
