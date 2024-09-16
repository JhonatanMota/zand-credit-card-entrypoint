package com.zand.creditcard.domain.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/** Class for bad request error types. */
public class BadRequestException extends ApiException {

  public BadRequestException(String message) {
    super(message, BAD_REQUEST);
  }

  public BadRequestException(String errorMessage, String errorDetails) {
    super(errorMessage, errorDetails, BAD_REQUEST);
  }
}
