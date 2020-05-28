package com.springboot.handler;

import com.springboot.Utils.RestResult;
import com.springboot.constant.RestResultCodeEnum;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by tangbo on 2018/1/30 0030.
 */
@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public RestResult handlerException(RuntimeException ex){
        ex.printStackTrace();
        return new RestResult(RestResultCodeEnum.EXCEPTION.code(), ex.getMessage());
    }

    /**
     * 捕获@Valid校验不通过，获取message
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public RestResult handlerVaildException(MethodArgumentNotValidException ex){
        ex.printStackTrace();
        return new RestResult(RestResultCodeEnum.EXCEPTION.code(), ex.getBindingResult().getFieldError().getDefaultMessage().toString());
    }
}
