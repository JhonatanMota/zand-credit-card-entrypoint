package com.zand.creditcard.web.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class MultipartFileValidator implements ConstraintValidator<NotEmptyMultipartFile, Object> {

  @Override
  public boolean isValid(Object value, ConstraintValidatorContext context) {
    var multipartFile = (MultipartFile) value;
    return !(multipartFile == null || multipartFile.isEmpty());
  }
}
