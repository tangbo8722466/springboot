package com.springboot.controller;

import com.springboot.Utils.RestResult;
import com.springboot.Utils.UserDefine;
import com.springboot.Vo.HelloCreateVo;
import com.springboot.Vo.HelloUpdateVo;
import com.springboot.repository.entity.HelloEntity;
import com.springboot.service.HelloService;
import io.swagger.annotations.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by tangbo on 2018/1/28 0028.
 */
@RestController
@RequestMapping("/v1/")
@Api(value = "hello")
public class HelloController {

    @Autowired
    HelloService helloService;

    @Autowired
    UserDefine userDefine;

    @Value("${test.msg}")
    private String msg;
    @RequestMapping(value = "/error",  method = RequestMethod.GET)
    String error() {
        int i = 1/0;
        return "HelloEntity World!";
    }

    @RequestMapping(value = "/hello",  method = RequestMethod.POST)
    RestResult<HelloEntity> create(@Valid @RequestBody HelloCreateVo helloCreateVo) {
        return helloService.save(new HelloEntity().builder().name(helloCreateVo.getName()).remark(helloCreateVo.getRemark()).build());
    }

    @RequestMapping(value = "/hello/{id}",  method = RequestMethod.PUT)
    RestResult<HelloEntity> update(@PathVariable("id") Integer id, @RequestBody HelloUpdateVo helloUpdateVo) {
        return helloService.update(new HelloEntity().builder().id(id).name(helloUpdateVo.getName()).remark(helloUpdateVo.getRemark()).build());
    }

    @RequestMapping(value = "/hello",  method = RequestMethod.GET)
    RestResult<List<HelloEntity>> getAll() {
        return helloService.list();
    }

    @RequestMapping(value = "/hello/{id}",  method = RequestMethod.GET)
    RestResult<HelloEntity> getById(@PathVariable("id") Integer id) {
        return helloService.getById(id);
    }

    @RequestMapping(value = "/hello/{id}",  method = RequestMethod.DELETE)
    RestResult delete(@PathVariable("id") Integer id) {
        return helloService.delete(id);
    }

    @RequestMapping(value = "/hello/page",  method = RequestMethod.GET)
    RestResult page(@RequestParam("int") int start, @RequestParam("limit") int limit, @RequestParam(name = "name", required = false) String name) {
        return helloService.page(start, limit, name);
    }

    @RequestMapping(value = "/hello/userdefine",  method = RequestMethod.GET)
    RestResult userdefine() {
        return new RestResult(RestResult.SUCCESS, null, userDefine.getName()+","+userDefine.getPasswd()+","+msg);
    }

}
