package com.springboot.controller;

import com.springboot.Utils.RestResult;
import com.springboot.Utils.RestResultStatusEnum;
import com.springboot.Utils.ShareMethodUtils;
import com.springboot.Utils.UserDefine;
import com.springboot.Vo.UserCreateVo;
import com.springboot.Vo.UserUpdateVo;
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
        return userService.save(new UserEntity().builder().userName(userCreateVo.getUserName()).account(userCreateVo.getAccount()).password(userCreateVo.getPassword()).remark(userCreateVo.getRemark()).build());
    }

    @RequestMapping(value = "/user/{id}",  method = RequestMethod.PUT)
    RestResult<UserEntity> update(@PathVariable("id") Long id, @RequestBody UserUpdateVo userUpdateVo) throws IllegalAccessException {
        if ( ObjectUtils.isEmpty(userUpdateVo) || ShareMethodUtils.checkAllObjFieldIsNull(userUpdateVo) ){
            return new RestResult(RestResultStatusEnum.FAIL.value(), "there is no things to update!");
        }

        UserEntity entity = userDao.findOne(id);
        if ( null == entity ) {
            return new RestResult(RestResultStatusEnum.FAIL.value(), "the user entity dose not exits!");
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
        return new RestResult(RestResultStatusEnum.SUCCESS.value(), null, userDefine.getName()+","+userDefine.getPasswd()+","+msg);
    }

    @RequestMapping(value = "/get/certificateproductinfo/{history_id}", method = RequestMethod.GET)
    String getErpTestDateForHistoryId(@PathVariable("history_id") Integer historyId){
        String waterKey = "{\"items\": [{\"ROW_ID\": 10421,\"HISTORY_ID\": 10361,\"CERTIFICATE_INV_ID\": 13642,\"SERIAL_NUMBER\": \"18-52-0001-CM\",\"CONTRACT_NUMBER\": \"NSF20181201010102436\",\"OEL_LINE_NUM\": \"1.1.8\",\"PRODUCT_NUMBER\": \"ESPC-NSPS-GOL(FY)-N01\",\"PRODUCT_DESC\": \"ESPC黄金服务包(首年)-基础版，包含产品系统升级授权、产品规则升级授权、远程支持、产品安装-基础版、安全通告服务；\",\"PRODUCT_TYPE\": \"ESPC\",\"PRODUCT_MODEL\": \"AURORA\",\"QUANTITY\": 1,\"PRIMARY_UOM_CODE\": \"S/Y\"},{\"ROW_ID\": 10422,\"HISTORY_ID\": 10361,\"CERTIFICATE_INV_ID\": 13642,\"SERIAL_NUMBER\": \"18-52-0001-CM\",\"CONTRACT_NUMBER\": \"NSF20181201010102436\",\"OEL_LINE_NUM\": \"1.1.10\",\"PRODUCT_NUMBER\": \"ESPC-NSPS-GOL(CE)-N01\",\"PRODUCT_DESC\": \"ESPC黄金服务包(续约)，包含产品系统升级授权、产品规则升级授权、远程支持、安全通告服务；\",\"PRODUCT_TYPE\": \"ESPC\",\"PRODUCT_MODEL\": \"AURORA\",\"QUANTITY\": 1,\"PRIMARY_UOM_CODE\": \"S/Y\"},{\"ROW_ID\": 10423,\"HISTORY_ID\": 10361,\"CERTIFICATE_INV_ID\": 13642,\"SERIAL_NUMBER\": \"18-52-0001-CM\",\"CONTRACT_NUMBER\": \"NSF20181201010102436\",\"OEL_LINE_NUM\": \"1.1.2\",\"PRODUCT_NUMBER\": \"ESPC-NOS-01\",\"PRODUCT_DESC\": \"ESPC引擎系统\",\"PRODUCT_TYPE\": \"ESPC\",\"PRODUCT_MODEL\": \"AURORA\",\"QUANTITY\": 1,\"PRIMARY_UOM_CODE\": \"PCS\"},{\"ROW_ID\": 10424,\"HISTORY_ID\": 10361,\"CERTIFICATE_INV_ID\": 13642,\"SERIAL_NUMBER\": \"18-52-0001-CM\",\"CONTRACT_NUMBER\": \"NSF20181201010102436\",\"OEL_LINE_NUM\": \"1.1.4\",\"PRODUCT_NUMBER\": \"WATERMARK\",\"PRODUCT_DESC\": \"虚拟化产品集中授权功能--本地授权。ESPC与加密狗联动，实现绿盟虚拟化产品的本地授权\",\"PRODUCT_TYPE\": \"ESPC\",\"PRODUCT_MODEL\": \"AURORA\",\"QUANTITY\": 1,\"PRIMARY_UOM_CODE\": \"PCS\"},{\"ROW_ID\": 10425,\"HISTORY_ID\": 10361,\"CERTIFICATE_INV_ID\": 13642,\"SERIAL_NUMBER\": \"18-52-0001-CM\",\"CONTRACT_NUMBER\": \"NSF20181201010102436\",\"OEL_LINE_NUM\": \"1.1.6\",\"PRODUCT_NUMBER\": \"ESPC-AEL-EX\",\"PRODUCT_DESC\": \"增加1个集群部署节点价格\",\"PRODUCT_TYPE\": \"ESPC\",\"PRODUCT_MODEL\": \"AURORA\",\"QUANTITY\": 1,\"PRIMARY_UOM_CODE\": \"PCS\"}]}";
        return waterKey;
    }
    @RequestMapping(value = "/get/certificatebaseinfo", method = RequestMethod.GET)
    String getErpBaseInfoFor() {
        return "{\"items\": [{\"CERTIFICATE_INV_ID\": 13642,\"HISTORY_ID\": 10361,\"PROJECT_NUMBER\": \"181228124445\",\"PROJECT_NAME\": \"证书自动化-ESPC产品-测试124445\",\"CONTRACT_NUMBER\": \"NSF20181201010102436\",\"PRODUCT_SERIES\": \"AURORA\",\"PRODUCT_TYPE\": \"ESPC\",\"PARTY_NUMBER\": \"199778\",\"PARTY_NAME\": \"浦发银行\",\"SERIAL_NUMBER\": \"18-52-0001-CM\",\"APPLY_NUMBER\": \"3627\",\"APPLY_NAME\": \"陈丹丹3627,\",\"APPLY_EMAIL\": \"songhuihui@nsfocus.com\",\"APPLY_DATE\": \"2019-01-09\",\"CUST_EMAIL1\": \"1336592249@qq.com\",\"CUST_EMAIL2\": null,\"CERT_START_DATE\": \"2019-01-09\",\"CERT_END_DATE\": \"2020-01-09\",\"CERT_TYPE_NAME\": \"临时\",\"DEPLOY_MODE\": null,\"COMMENTS\": null,\"MAKE_STATE\": \"制作中\",\"MAKE_DATE\": \"2019-01-24\",\"verification_method\": null}]}";
    }
}
