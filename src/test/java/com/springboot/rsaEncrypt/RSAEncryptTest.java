package com.springboot.rsaEncrypt;

import com.alibaba.fastjson.JSONObject;
import com.springboot.Utils.RestResult;
import com.springboot.Vo.request.DataEncryptVo;
import com.springboot.Vo.response.Empty;
import com.springboot.repository.entity.UserEntity;
import com.springboot.service.biz.DataEncryptBiz;
import com.springboot.service.impl.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @ClassName RSAEncryptTest
 * @Author sangfor for tangbo
 * @Description //TODO
 * @Date 2020/5/21 16:08
 * @Version 1.0.0
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class RSAEncryptTest {
    @Autowired
    DataEncryptBiz dataEncryptBiz;

    @Autowired
    UserService userService;
    @Test
    public void test() throws Exception {
        RestResult<List<UserEntity>> data = userService.list();
        String dataIn = JSONObject.toJSONString(data);
        log.info("待加密数据：" + dataIn);
        DataEncryptVo encryptVo = dataEncryptBiz.encryptData(dataIn);
        log.info("加密后数据：" + JSONObject.toJSONString(encryptVo));
        String dataOut = dataEncryptBiz.decodeData(encryptVo);
        log.info("解密数据：" + dataIn);
        if (dataIn.contains(dataOut)) {
            log.info("解密数据成功");
        }
        else{
            log.info("解密数据失败");
        }

    }
}
