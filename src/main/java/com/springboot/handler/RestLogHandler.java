package com.springboot.handler;

import net.sf.json.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by tangbo on 2018/3/13 0013.
 */
@Aspect
@Component
public class RestLogHandler {
    private Logger logger = Logger.getLogger(RestLogHandler.class);

    @Pointcut("execution(* com.springboot.controller.*.*(..))")
    public void exec(){

    }

    @Before("exec()")
    public void logStart(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request =  attributes.getRequest();
        //url
        logger.info("url = "+request.getRequestURI());
        //method
        logger.info("method= "+request.getMethod());
        //ip
        logger.info("ip="+request.getRemoteAddr());
        //类方法
        logger.info("class_method="+joinPoint.getSignature().getDeclaringTypeName()+'.'+ joinPoint.getSignature().getName()+ "job start");//获取类名及类方法
        //参数
        logger.info("args="+joinPoint.getArgs());
    }

    @After("exec()")
    public void logEnd(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request =  attributes.getRequest();
        //类方法
        logger.info("class_method="+joinPoint.getSignature().getDeclaringTypeName()+'.'+ joinPoint.getSignature().getName()+" job end");//获取类名及类方法
    }

    @AfterReturning(pointcut = "exec()", returning = "obj")
    public void restResponse(Object obj){
        if(obj instanceof String || obj instanceof Integer){
            logger.info("response:"+ obj.toString());
        }

        try {
            JSONObject jsObject = JSONObject.fromObject(obj);
            logger.info("response:"+ jsObject.toString());
        } catch (Exception e) {
            logger.info("response:"+ obj.toString());
        }


    }

}
