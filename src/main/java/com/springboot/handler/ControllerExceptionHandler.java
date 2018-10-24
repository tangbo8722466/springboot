package com.springboot.handler;

import com.springboot.Utils.RestResult;
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
        return new RestResult(RestResult.FAIL, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public RestResult handlerVaildException(MethodArgumentNotValidException ex){
        return new RestResult(RestResult.FAIL, ex.getBindingResult().getFieldError().getDefaultMessage().toString());
    }
}
