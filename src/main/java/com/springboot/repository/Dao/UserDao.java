package com.springboot.repository.Dao;

import com.springboot.repository.entity.UserEntity;
import org.springframework.stereotype.Repository;

/**
 * Created by tangbo on 2018/1/30 0030.
 */
@Repository
public interface UserDao extends BaseDao<UserEntity, Integer>{
}
