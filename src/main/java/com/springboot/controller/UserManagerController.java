package com.springboot.controller;

import com.springboot.Utils.RestResult;
import com.springboot.constant.RestResultCodeEnum;
import com.springboot.Utils.ShareMethodUtils;
import com.springboot.Utils.UserDefine;
import com.springboot.Vo.request.UserCreateVo;
import com.springboot.Vo.request.UserUpdateVo;
import com.springboot.repository.Dao.UserDao;
import com.springboot.repository.entity.UserEntity;
import com.springboot.service.impl.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by tangbo on 2018/1/28 0028.
 */
@RestController
@RequestMapping("/v1/")
@Api(value = "user_manger")
public class UserManagerController {

    @Autowired
    UserService userService;

    @Autowired
    UserDao userDao;

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
        userService.health();
        return userService.save(new UserEntity().builder().userName(userCreateVo.getUserName()).account(userCreateVo.getAccount()).password(userCreateVo.getPassword()).remark(userCreateVo.getRemark()).build());
    }

    @RequestMapping(value = "/user/{id}",  method = RequestMethod.PUT)
    RestResult<UserEntity> update(@PathVariable("id") Long id, @RequestBody UserUpdateVo userUpdateVo) throws IllegalAccessException {
        if ( ObjectUtils.isEmpty(userUpdateVo) || ShareMethodUtils.checkAllObjFieldIsNull(userUpdateVo) ){
            return new RestResult(RestResultCodeEnum.FAIL.code(), "there is no things to update!");
        }

        UserEntity entity = userDao.findOne(id);
        if ( null == entity ) {
            return new RestResult(RestResultCodeEnum.FAIL.code(), "the user entity dose not exits!");
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
        return userService.update(entity);
    }

    @RequestMapping(value = "/user",  method = RequestMethod.GET)
    RestResult<List<UserEntity>> getAll() {
        return userService.list();
    }

    @RequestMapping(value = "/user/{id}",  method = RequestMethod.GET)
    RestResult<UserEntity> getById(@PathVariable("id") Long id) {
        return userService.getById(id);
    }

    @RequestMapping(value = "/user/{id}",  method = RequestMethod.DELETE)
    RestResult delete(@PathVariable("id") Long id) {
        return userService.delete(id);
    }

    @RequestMapping(value = "/user/page",  method = RequestMethod.GET)
    RestResult page(@RequestParam("pageNumber") Integer pageNumber, @RequestParam("pageSize") Integer pageSize, @RequestParam(name = "name", required = false) String name) {
        return userService.page(pageNumber, pageSize, name);
    }

    @RequestMapping(value = "/user/userdefine",  method = RequestMethod.GET)
    RestResult userdefine() {
        return new RestResult(RestResultCodeEnum.SUCCESS.code(), null, userDefine.getName()+","+userDefine.getPasswd()+","+msg);
    }
}
