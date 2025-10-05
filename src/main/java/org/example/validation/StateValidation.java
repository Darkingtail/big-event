package org.example.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.anno.State;

public class StateValidation implements ConstraintValidator<State, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            // 禁用默认的错误提示消息，使用自定义的消息
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("状态不能为空").addConstraintViolation();
            return false;
        }

        if (!"已发布".equals(s) && !"草稿".equals(s)) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("状态必须是\"已发布\"或者\"草稿\"中的一个").addConstraintViolation();
            return false;
        }

        return true;
    }
}