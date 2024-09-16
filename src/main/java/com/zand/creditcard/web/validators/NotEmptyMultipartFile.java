package com.zand.creditcard.web.validators;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = MultipartFileValidator.class)
public @interface NotEmptyMultipartFile {

  String message() default "must not be empty";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
