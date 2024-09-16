package com.zand.creditcard.domain.exception;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import lombok.NoArgsConstructor;

/** Class for all application runtime exceptions. */
@NoArgsConstructor
public class ApplicationException extends ApiException {

  public ApplicationException(String message) {
    super(message, INTERNAL_SERVER_ERROR);
  }

  public ApplicationException(Throwable cause) {
    super(cause.getMessage(), null, INTERNAL_SERVER_ERROR, cause);
  }

  public ApplicationException(String message, Throwable cause) {
    super(message, null, INTERNAL_SERVER_ERROR, cause);
  }
}
