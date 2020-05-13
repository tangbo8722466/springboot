package com.springboot.aspect;

import com.framework.utils.core.api.ApiConst;
import com.framework.utils.core.api.ApiResponse;
import com.framework.utils.core.bean.PageResponse;
import com.springboot.Utils.DataEncryptUtils;
import com.springboot.Utils.PageInfo;
import com.springboot.Utils.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @ClassName DataencryptAspect
 * @Author sangfor for tangbo
 * @Description //TODO
 * @Date 2020/5/11 15:24
 * @Version 1.0.0
 **/
@Aspect
@Component
@Slf4j
public class DataEncryptAspect {

    private static List<String> idsFieldNames = Stream.of("idCard","idCardNo", "certifId", "certNo")
            .collect(Collectors.toList());

    private static List<String> phoneFieldNames = Stream.of("phoneNumber", "mobile", "phone", "mobileNo", "oBankMobileNo",
            "phoneNum", "memberPhone").collect(Collectors.toList());

    private static List<String> uidFieldNames = Stream.of("uid", "memberId").collect(Collectors.toList());

    private static List<String> bankCardFieldNames = Stream.of("bankCardNo", "accountNo", "oBankAccountNo","rootAccNo",
            "subAccNo", "vAccNo", "recAccount", "payAccount", "bandCardId").collect(Collectors.toList());

    //设置切入点
    @Pointcut("@annotation(com.springboot.aspect.DataEncryptAnno)")
    public void encrypt(){

    }

    /**
     * 修改入参带*
     * @param obj
     * @return
     */
//    @Around("encrypt()")
//    public Object proceed(ProceedingJoinPoint joinPoint) throws Throwable{
//        Object[] args = joinPoint.getArgs();  //获取目标方法的入参
//        Object[] argsReflect = new Object[args.length];
//        List<String> fieldNames = composeField();
//        for (int i=0; i<args.length; i++){
//            argsReflect[i] = requestReflect(args[i], fieldNames);
//        }
//        Object result = joinPoint.proceed(argsReflect);  //执行目标方法
//        return result;
//    }

    @AfterReturning(pointcut = "encrypt()", returning = "obj")
    public Object encryptResponse(Object obj){
        if (! (obj instanceof RestResult)) {
            return obj;
        }

        Class cls = obj.getClass();
        try {
            Field codeField = cls.getDeclaredField("code");
            codeField.setAccessible(true);
            int code = (int)codeField.get(obj);
            if (ApiConst.Code.CODE_SUCCESS.code() != code){
                return obj;
            }
            Field dataField = cls.getDeclaredField("data");
            dataField.setAccessible(true);
            Object data = dataField.get(obj);
            List<String> fieldNames = composeField();
            if (data instanceof PageInfo) {
                PageInfo pageInfo = (PageInfo) data;
                pageInfo.setItems(DataEncryptAspect.responseListReflect(pageInfo.getItems(), fieldNames));
                dataField.set(obj, pageInfo);
            }
            if (data instanceof List) {
                dataField.set(obj, DataEncryptAspect.responseListReflect((List)data, fieldNames));
            }
            else{
                dataField.set(obj, DataEncryptAspect.responseReflect(data, fieldNames));
            }
        } catch (NoSuchFieldException e) {
            //e.printStackTrace();
        } catch (IllegalAccessException e) {
            //e.printStackTrace();
        }
        return obj;
    }

    public static Object requestReflect(Object obj,  List<String> fieldNames){
        Class objClass = obj.getClass();
        fieldNames.stream().forEach(fieldName -> {
            try {
                Field field = objClass.getDeclaredField(fieldName);
                field.setAccessible(true);
                String fieldStr = (String) field.get(obj);
                if (StringUtils.isNoneBlank(fieldStr) && fieldStr.contains("*")) {
                    field.set(obj, null);
                }
            } catch (NoSuchFieldException e) {
                //e.printStackTrace();
            } catch (IllegalAccessException e) {
                //e.printStackTrace();
            }
        });
        return obj;
    }

    public static List<String> composeField() {
        List<String> list = new ArrayList<>();
        list.addAll(idsFieldNames);
        list.addAll(phoneFieldNames);
        list.addAll(uidFieldNames);
        list.addAll(bankCardFieldNames);
        return list;
    }

    public static Object responseReflect(Object obj, List<String> fieldNames){
        Class objClass = obj.getClass();
        fieldNames.stream().forEach(fieldName -> {
                try {
                    Field field = objClass.getDeclaredField(fieldName);
                    field.setAccessible(true);
                    field.set(obj, encrypt(fieldName, (String)field.get(obj)));
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        );
        return obj;
    }

    public static List<Object> responseListReflect(List<Object> objs, List<String> fieldNames){
        List<Object> objsReflect = new ArrayList<>();
        objs.stream().forEach(obj ->{
            Class objClass = obj.getClass();
            fieldNames.stream().forEach(fieldName -> {
                try {
                    Field field = objClass.getDeclaredField(fieldName);
                    field.setAccessible(true);
                    field.set(obj, encrypt(fieldName, (String)field.get(obj)));
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
            objsReflect.add(obj);
        });
        return objsReflect;
    }

    public static String encrypt(String fieldName, String content){
        if (idsFieldNames.contains(fieldName)) {
            return DataEncryptUtils.idEncrypt(content);
        }

        if (phoneFieldNames.contains(fieldName)) {
            return DataEncryptUtils.mobileEncrypt(content);
        }

        if (bankCardFieldNames.contains(fieldName)) {
            return DataEncryptUtils.idCardEncrypt(content);
        }

        if (uidFieldNames.contains(fieldName)){
            return DataEncryptUtils.idEncrypt(content);
        }

        return content;
    }
}
