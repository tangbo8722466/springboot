package com.springboot.excel;

import org.hibernate.validator.HibernateValidator;

import javax.validation.*;
import java.util.Set;

/**
 * @Description:
 * @Author : pc.huang
 * @Date : 2019-05-09 22:00
 */
public class ValidatorUtil {

    /**
     * 获取验证bean对象
     *
     * @return
     */
    public static Validator createValidator() {
        Configuration<?> config = Validation.byDefaultProvider().configure();
        ValidatorFactory factory = config.buildValidatorFactory();
        Validator validator = factory.getValidator();
        factory.close();
        return validator;
    }

    /**
     * 组装异常信息
     *
     * @param set
     * @param <T>
     * @return
     */
    public static <T> String error(Set<ConstraintViolation<T>> set) {
        StringBuilder stringBuilder = new StringBuilder();
        set.forEach(a -> {
            stringBuilder.append(a.getMessage()).append(",").append("\r\n");
        });
        return stringBuilder.toString();
    }


    /**
     * 校验对象并返回失败数据
     *
     * @param isFailFast 是否快速失败返回模式
     * @return
     */
    public static String validate(Object obj, boolean isFailFast) {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                .failFast(isFailFast)
                .buildValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        return error(validator.validate(obj));
    }

}
