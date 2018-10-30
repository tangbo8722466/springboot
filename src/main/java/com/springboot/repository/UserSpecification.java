package com.springboot.repository;

import com.springboot.repository.entity.UserEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tangbo on 2018/2/1 0001.
 */
public class UserSpecification implements Specification<UserEntity> {
    private UserEntity userEntity;

    public UserSpecification(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @Override
    public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> list = new ArrayList<Predicate>();
        if (!StringUtils.isEmpty(userEntity.getUserName())) {
            list.add(criteriaBuilder.equal(root.get("userName"), userEntity.getUserName()));
        }
        criteriaQuery.where(list.toArray(new Predicate[list.size()]));
        return null;
    }
}
