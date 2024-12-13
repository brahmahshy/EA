package com.easyacg.core.validate;

import com.easyacg.core.entity.EasyacgException;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 校验入参必须为有效的枚举值
 *
 * @author brahma
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Constraint(validatedBy = MustHasOne.MustHasOneValidator.class)
public @interface MustHasOne {
    /**
     * 需要校验的枚举类
     */
    String[] value();

    String message() default "必须为有效的枚举值：{enumValue}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class MustHasOneValidator implements ConstraintValidator<MustHasOne, Object> {
        private List<String> mustOneParams;

        @Override
        public void initialize(MustHasOne constraintAnnotation) {
            this.mustOneParams = Arrays.asList(constraintAnnotation.value());
        }

        @Override
        public boolean isValid(Object value, ConstraintValidatorContext context) {
            if (value == null) {
                return false;
            }
            boolean hasOne = false;
            for (String param : mustOneParams) {
                try {
                    Field field = value.getClass().getDeclaredField(param);
                    field.setAccessible(true);
                    Object paramValue = field.get(value);
                    if (paramValue instanceof CharSequence str && StringUtils.isBlank(str)) {
                        hasOne = true;
                        break;
                    }

                    if (paramValue instanceof Collection<?> list && CollectionUtils.isNotEmpty(list)) {
                        hasOne = true;
                        break;
                    }
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    // 如果字段不存在或访问失败，继续下一个字段
                    throw EasyacgException.build("参数配置异常，请联系管理员！", e);
                }
            }
            return hasOne;
        }
    }
}
