package org.example.anno;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.validation.StateValidation;

import java.lang.annotation.*;

@Documented
@Constraint(
        validatedBy = {StateValidation.class}
)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface State {
    String message() default "必须是“已发布”或者“草稿”中的一个";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
