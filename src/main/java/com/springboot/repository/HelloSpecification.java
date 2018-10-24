package com.springboot.repository;

import com.springboot.repository.entity.HelloEntity;
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
public class HelloSpecification implements Specification<HelloEntity> {
    private HelloEntity hello;

    public HelloSpecification(HelloEntity hello) {
        this.hello = hello;
    }

    @Override
    public Predicate toPredicate(Root<HelloEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> list = new ArrayList<Predicate>();
        if (!StringUtils.isEmpty(hello.getName())) {
            list.add(criteriaBuilder.equal(root.get("name"), hello.getName()));
        }
        criteriaQuery.where(list.toArray(new Predicate[list.size()]));
        return null;
    }
}
