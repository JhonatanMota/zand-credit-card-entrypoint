package com.zand.creditcard.domain.handler.error;

import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang3.StringUtils.SPACE;

import com.zand.creditcard.domain.exception.ApiErrorMessageProvider;
import com.zand.creditcard.domain.exception.ApiException;
import com.zand.creditcard.domain.exception.ApplicationException;
import com.zand.creditcard.domain.exception.BadRequestException;
import com.zand.creditcard.domain.exception.NotFoundException;
import com.zand.creditcard.domain.handler.error.dto.ErrorResponse;
import com.zand.creditcard.domain.handler.error.dto.ErrorResponseBuilder;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/** Class for global error handling for REST API. */
@Slf4j
@RestControllerAdvice
@Order(3)
public class ErrorHandler {

  @ExceptionHandler(ApiException.class)
  protected ResponseEntity<ErrorResponse> handleApiException(ApiException e) {

    log.error("{} {}", e.getMessage(), e.getCause() != null ? e.getCause() : StringUtils.EMPTY);

    return new ResponseEntity<>(buildErrorResponse(e), e.getHttpStatus());
  }

  @ExceptionHandler(ConstraintViolationException.class)
  protected ResponseEntity<ErrorResponse> handleConstraintViolationException(
      ConstraintViolationException e) {

    var message =
        e.getConstraintViolations().stream()
            .map(
                err ->
                    err.getConstraintDescriptor()
                            .getAnnotation()
                            .annotationType()
                            .getName()
                            .contains("NotEmptyMultipartFile")
                        ? err.getMessage()
                        : err.getPropertyPath() + SPACE + err.getMessage())
            .collect(joining(";"));

    var error = new BadRequestException(message);

    return handleApiException(error);
  }

  @ExceptionHandler(
      value = {
        MissingServletRequestParameterException.class,
        HttpMediaTypeNotSupportedException.class,
        MethodArgumentTypeMismatchException.class
      })
  protected ResponseEntity<ErrorResponse> handleInvalidRequestException(Exception e) {

    log.error(e.getMessage());

    var error = new BadRequestException(e.getMessage());

    return new ResponseEntity<>(buildErrorResponse(error), error.getHttpStatus());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {

    var message =
        e.getFieldErrors().stream()
            .map(err -> err.getField() + SPACE + err.getDefaultMessage())
            .collect(joining(";"));

    var error = new BadRequestException(message);

    return handleApiException(error);
  }

  @ExceptionHandler(HttpMessageConversionException.class)
  protected ResponseEntity<ErrorResponse> handleHttpMessageConversionException(
      HttpMessageConversionException e) {

    var error = new BadRequestException("Request payload is missing", e.getMessage());

    return handleApiException(error);
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(
      HttpRequestMethodNotSupportedException e) {

    var error = new BadRequestException(e.getMessage());

    return handleApiException(error);
  }

  @ExceptionHandler(Exception.class)
  protected ResponseEntity<ErrorResponse> handleAnyException(Exception e) {

    var error = new ApplicationException(e);

    return handleApiException(error);
  }

  @ExceptionHandler(NoResourceFoundException.class)
  protected ResponseEntity<ErrorResponse> handleNoResourceFoundException(
      NoResourceFoundException e) {

    var error = new NotFoundException(e.getMessage());

    return handleApiException(error);
  }

  private static ErrorResponse buildErrorResponse(ApiErrorMessageProvider errorMessageProvider) {
    return new ErrorResponseBuilder(errorMessageProvider).build();
  }
}
