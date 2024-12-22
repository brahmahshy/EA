package com.easyacg.core.validate;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import java.lang.annotation.*;
import java.util.Arrays;

/**
 * 校验入参必须为有效的枚举值
 *
 * @author brahma
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = IsEnum.IsEnumValidator.class)
public @interface IsEnum {
    /**
     * 需要校验的枚举类
     */
    Class<? extends Enum<?>> enumClass();

    String message() default "必须为有效的枚举值：{enumValue}";

    boolean ignoreCase() default true;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class IsEnumValidator implements ConstraintValidator<IsEnum, Object> {
        private boolean ignoreCase;

        private Class<? extends Enum<?>> enumClass;

        @Override
        public void initialize(IsEnum constraintAnnotation) {
            this.ignoreCase = constraintAnnotation.ignoreCase();
            this.enumClass = constraintAnnotation.enumClass();
        }

        @Override
        public boolean isValid(Object value, ConstraintValidatorContext context) {
            if (value == null) {
                return true;
            }

            try {
                Enum<?>[] enumConstants = enumClass.getEnumConstants();

                // 设置消息变量
                context.unwrap(HibernateConstraintValidatorContext.class)
                        .addMessageParameter("enumValue", Arrays.toString(enumConstants));

                // 校验值是否合法
                for (Enum<?> enumValue : enumConstants) {
                    if (ignoreCase && enumValue.name().equalsIgnoreCase(value.toString())) {
                        return true;
                    }
                    
                    if (enumValue.name().equals(value.toString())) {
                        return true;
                    }
                }

                return false;
            } catch (Exception e) {
                return false;
            }
        }
    }
}
