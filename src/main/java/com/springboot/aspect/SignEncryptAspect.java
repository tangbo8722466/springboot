package com.springboot.aspect;

import com.springboot.Utils.SpringBeanUtils;
import com.springboot.Vo.request.DataEncryptVo;
import com.springboot.service.EncryptObjectRedisService;
import com.springboot.service.biz.DataEncryptBiz;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * @ClassName DataencryptAspect
 * @Author sangfor for tangbo
 * @Description 数据脱敏
 * @Date 2020/5/11 15:24
 * @Version 1.0.0
 **/
@Aspect
@Component
@Slf4j
public class SignEncryptAspect {

    @Value("${rsa.public.key:1}")
    private String rsaPublicKey;

    @Value("${rsa.private.key:2}")
    private String rsaPrivateKey ;

    //设置切入点
    @Pointcut("@annotation(com.springboot.aspect.SignEncryptAnno)")
    public void encrypt(){

    }

    /**
     * 请求参数验签，同步设置请求参数
     * @param joinPoint
     * @return
     */
//    @Before("encrypt()")
//    public void proceed(JoinPoint joinPoint){
//        Object[] args = joinPoint.getArgs();  //获取目标方法的入参
//        DataEncryptBiz dataEncryptBiz = SpringBeanUtils.getBean(DataEncryptBiz.class);
//        EncryptObjectRedisService encryptObjectRedisService = SpringBeanUtils.getBean(EncryptObjectRedisService.class);
//        DataEncryptVo data = (DataEncryptVo) args[0];
//        String json = null;
//        try {
//            json = dataEncryptBiz.decodeDataByPrivateKey(data, rsaPrivateKey);
//        } catch (Exception e) {
//            json = e.getMessage();
//            e.printStackTrace();
//        }
//        encryptObjectRedisService.set(data.getSkey(), json, 30);
//    }

    /**
     * @param joinPoint
     * @return
     */
    @Around("encrypt()")
    public Object proceed(ProceedingJoinPoint joinPoint) throws Throwable{
        //获取目标方法的入参
        Object[] args = joinPoint.getArgs();
        DataEncryptBiz dataEncryptBiz = SpringBeanUtils.getBean(DataEncryptBiz.class);
        EncryptObjectRedisService encryptObjectRedisService = SpringBeanUtils.getBean(EncryptObjectRedisService.class);
        DataEncryptVo data = (DataEncryptVo) args[0];
        String json = null;
        try {
            json = dataEncryptBiz.decodeDataByPrivateKey(data, rsaPrivateKey);
        } catch (Exception e) {
            json = e.getMessage();
            e.printStackTrace();
        }
        encryptObjectRedisService.set(data.getSkey(), json, 30);
        data.setData(json);
        args[0] = data;
        //执行目标方法
        Object result = joinPoint.proceed(args);
        return result;
    }

    @AfterReturning(pointcut = "encrypt()", returning = "obj")
    public void encryptResponse(Object obj){
        if (!(obj instanceof DataEncryptVo)) {
            return;
        }
        //通过反射加密响应结果
        DataEncryptVo encryptObj = encrypt((DataEncryptVo)obj);
        Class objClass = obj.getClass();
        try {
            Field skeyField = objClass.getDeclaredField("skey");
            Field bodyField = objClass.getDeclaredField("body");
            skeyField.setAccessible(true);
            bodyField.setAccessible(true);
            skeyField.set(obj, encryptObj.getSkey());
            bodyField.set(obj, encryptObj.getBody());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @AfterThrowing(pointcut = "encrypt()", throwing = "throwable")
    public void afterThrowing(Throwable throwable){
        log.info("异常抛出通知:"+throwable);
    }

    /**
     * 加密响应结果
     * @param dataOrigin
     * @return
     */
    public DataEncryptVo encrypt(DataEncryptVo dataOrigin) {
        DataEncryptBiz dataEncryptBiz = SpringBeanUtils.getBean(DataEncryptBiz.class);
        try {
            DataEncryptVo result = dataEncryptBiz.encryptDataByPublicKey(dataOrigin.getBody(), rsaPublicKey);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
