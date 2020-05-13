package com.springboot.service.biz;

import com.springboot.Utils.PageInfo;
import com.springboot.Utils.RestResult;
import com.springboot.constant.RestResultCodeEnum;
import com.springboot.repository.Dao.UserDao;
import com.springboot.repository.UserSpecification;
import com.springboot.repository.entity.UserEntity;
import com.springboot.service.UserRedisServiceImpl;
import com.springboot.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by tangbo on 2018/1/30 0030.
 */
@Component
@Transactional
public class UserBiz{
    @Autowired
    UserDao userDao;

    @Autowired
    UserRedisServiceImpl userRedisService;
// 注释缓存
//    @CacheEvict(value="userCache", allEntries=true)
    public UserEntity save(UserEntity hello) {
        UserEntity result = userDao.save(hello);
//        userRedisService.put("user_"+ hello.getId(), hello, -1);
//        UserEntity userEntity = userRedisService.get("user_"+ hello.getId());
        return result;
    }

//    @CacheEvict(value="userCache", allEntries=true)
    public UserEntity update(UserEntity hello) {
        return userDao.saveAndFlush(hello);
    }

//    @Cacheable(value="userCache") //缓存,这里没有指定key.
    public UserEntity findById(Long id) {
        return userDao.findOne(id);
    }

    public UserEntity findOneByAccount(String account) {
        return userDao.findOne(new UserSpecification(UserEntity.builder().account(account).build()));
    }


//    @Cacheable(value="userCache")
    public List<UserEntity> list() {
        return userDao.findAll();
    }

    //allEntries 清空缓存所有属性 确保更新后缓存刷新
//    @CacheEvict(value="userCache", allEntries=true)
    public void delete(Long id) {
        userDao.delete(id);
    }

//    @Cacheable(value="userCache", key="#p0+-+#p1")
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
