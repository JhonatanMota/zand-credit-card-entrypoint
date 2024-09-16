package com.zand.creditcard.domain.exception;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/** Base abstract class for all API runtime exceptions. */
@Getter
@NoArgsConstructor
public abstract class ApiException extends RuntimeException implements ApiErrorMessageProvider {

  @SuppressWarnings("java:S1165")
  private HttpStatus httpStatus = INTERNAL_SERVER_ERROR;

  @SuppressWarnings("java:S1165")
  private String details;

  protected ApiException(String message) {
    super(message);
  }

  protected ApiException(String errorMessage, HttpStatus httpStatus) {
    this(errorMessage);
    this.httpStatus = httpStatus;
  }

  protected ApiException(String errorMessage, String details, HttpStatus httpStatus) {
    this(errorMessage);
    this.details = details;
    this.httpStatus = httpStatus;
  }

  protected ApiException(String message, String details, Throwable cause) {
    super(message, cause);
    this.details = details;
  }

  protected ApiException(String message, String details, HttpStatus httpStatus, Throwable cause) {
    this(message, details, cause);
    this.httpStatus = httpStatus;
  }
}
