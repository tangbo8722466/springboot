package com.springboot.lamb;

import com.fasterxml.jackson.annotation.JsonValue;
import com.springboot.lamb.impl.MyLamda;
import com.springboot.repository.entity.UserEntity;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class MyLamdaTest {
    @Test
    public void test(){
        MyLamda myLamda = x -> System.out.println(x);
        myLamda.test1("test");
    }

    @Test
    public void test1(){
       List<UserEntity> list = new ArrayList<>();
       list.stream().filter(a->a.getAccount().equalsIgnoreCase("test")).collect(Collectors.toList());
    }

    @Test
    public void test2() {
//        map中获取的返回值自动被Optional包装,即返回值 -> Optional<返回值>
//        flatMap中返回值保持不变,但必须是Optional类型,即Optional<返回值> -> Optional<返回值>
        UserEntity user = new UserEntity();
        user.setId(1L);
        Optional<UserEntity> optionalUserEntity = Optional.ofNullable(user);
        Optional<Long> id1 = optionalUserEntity.map(UserEntity::getId);
        id1.ifPresent(id -> System.out.println(id));
        Optional<Long> id2 = optionalUserEntity.flatMap(MyLamdaTest::getId);
        id2.ifPresent(id -> System.out.println(id));
    }

    public static Optional<Long> getId(UserEntity userEntity){
        return Optional.ofNullable(userEntity).map(UserEntity::getId);
    }
}
