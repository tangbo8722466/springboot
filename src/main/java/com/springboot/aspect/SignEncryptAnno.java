package com.springboot.aspect;

import com.springboot.Utils.SpringBeanUtils;
import com.springboot.Vo.request.DataEncryptVo;
import com.springboot.service.EncryptObjectRedisService;
import com.springboot.service.biz.DataEncryptBiz;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 数据验签
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Constraint(validatedBy = {SignEncryptAnno.EmptyChecker.class})
@Documented
public @interface SignEncryptAnno {
    String message() default "请求的加密参数不能为空";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class EmptyChecker implements ConstraintValidator<SignEncryptAnno, DataEncryptVo> {
        @Override
        public void initialize(SignEncryptAnno arg0) {
        }

        @Override
        public boolean isValid(DataEncryptVo data, ConstraintValidatorContext context) {
            if (data == null || StringUtils.isBlank(data.getSkey()) || StringUtils.isBlank(data.getBody())) {
                return false;
            }
            return true;
        }
    }

}
