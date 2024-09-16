package com.zand.creditcard.domain.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

/** Class for "not found" exceptions. */
public class NotFoundException extends ApiException {

  public NotFoundException(String message) {
    super(message, NOT_FOUND);
  }
}
