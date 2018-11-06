package com.springboot.controller;

import com.springboot.Utils.RestResult;
import com.springboot.Utils.ShareMethodUtils;
import com.springboot.Utils.UserDefine;
import com.springboot.Vo.UserCreateVo;
import com.springboot.Vo.UserUpdateVo;
import com.springboot.repository.Dao.UserDao;
import com.springboot.repository.entity.UserEntity;
import com.springboot.service.HelloService;
import io.swagger.annotations.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import sun.invoke.empty.Empty;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * Created by tangbo on 2018/1/28 0028.
 */
@RestController
@RequestMapping("/v1/")
@Api(value = "user_manger")
public class UserManagerController {

    @Autowired
    HelloService helloService;

    @Autowired
    UserDao helloDao;

    @Autowired
    UserDefine userDefine;

    @Value("${test.msg}")
    private String msg;
    @RequestMapping(value = "/error",  method = RequestMethod.GET)
    String error() {
        int i = 1/0;
        return "UserEntity World!";
    }

    @RequestMapping(value = "/user",  method = RequestMethod.POST)
    RestResult<UserEntity> create(@Valid @RequestBody UserCreateVo userCreateVo) {
        return helloService.save(new UserEntity().builder().userName(userCreateVo.getUserName()).account(userCreateVo.getAccount()).password(userCreateVo.getPassword()).remark(userCreateVo.getRemark()).build());
    }

    @RequestMapping(value = "/user/{id}",  method = RequestMethod.PUT)
    RestResult<UserEntity> update(@PathVariable("id") Long id, @RequestBody UserUpdateVo userUpdateVo) throws IllegalAccessException {
        if ( ObjectUtils.isEmpty(userUpdateVo) || ShareMethodUtils.checkAllObjFieldIsNull(userUpdateVo) ){
            return new RestResult(RestResult.FAIL, "there is no things to update!");
        }

        UserEntity entity = helloDao.findOne(id);
        if ( null == entity ) {
            return new RestResult(RestResult.FAIL, "the user entity dose not exits!");
        }

        if ( !StringUtils.isEmpty(userUpdateVo.getUserName()) ) {
            entity.setUserName(userUpdateVo.getUserName());
        }

        if ( !StringUtils.isEmpty(userUpdateVo.getPassword()) ) {
            entity.setPassword(userUpdateVo.getPassword());
        }

        if ( userUpdateVo.getRemark() != null ) {
            entity.setRemark(userUpdateVo.getRemark());
        }
        return helloService.update(entity);
    }

    @RequestMapping(value = "/user",  method = RequestMethod.GET)
    RestResult<List<UserEntity>> getAll() {
        return helloService.list();
    }

    @RequestMapping(value = "/user/{id}",  method = RequestMethod.GET)
    RestResult<UserEntity> getById(@PathVariable("id") Long id) {
        return helloService.getById(id);
    }

    @RequestMapping(value = "/user/{id}",  method = RequestMethod.DELETE)
    RestResult delete(@PathVariable("id") Long id) {
        return helloService.delete(id);
    }

    @RequestMapping(value = "/user/page",  method = RequestMethod.GET)
    RestResult page(@RequestParam("int") int start, @RequestParam("limit") int limit, @RequestParam(name = "name", required = false) String name) {
        return helloService.page(start, limit, name);
    }

    @RequestMapping(value = "/user/userdefine",  method = RequestMethod.GET)
    RestResult userdefine() {
        return new RestResult(RestResult.SUCCESS, null, userDefine.getName()+","+userDefine.getPasswd()+","+msg);
    }

}
