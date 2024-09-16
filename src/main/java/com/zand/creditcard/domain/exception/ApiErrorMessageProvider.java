package com.zand.creditcard.domain.exception;

import org.springframework.http.HttpStatus;

/** Interface for providing API error information. */
public interface ApiErrorMessageProvider {
  HttpStatus getHttpStatus();

  String getMessage();

  String getDetails();
}
