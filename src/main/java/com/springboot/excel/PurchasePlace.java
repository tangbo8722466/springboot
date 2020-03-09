package com.springboot.excel;

import org.apache.commons.lang3.StringUtils;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Auther: tangbo
 * @Date:
 * @Description: 自定义采购地校验
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PurchasePlace.Valid.class)
public @interface PurchasePlace {
    String message() default "采购地只支持国内/国外";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class Valid implements ConstraintValidator<PurchasePlace, String> {

        @Override
        public void initialize(PurchasePlace constraintAnnotation) {

        }

        @Override
        public boolean isValid(String s, ConstraintValidatorContext context) {
            if (StringUtils.isBlank(s)){
                return false;
            }
            if(s.equalsIgnoreCase("国内") || s.equalsIgnoreCase("国外")) {
                return true;
            }
            return false;
        }
    }
}
