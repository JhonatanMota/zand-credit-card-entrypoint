package com.zand.creditcard.domain.handler.error.dto;

import com.zand.creditcard.domain.exception.ApiErrorMessageProvider;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;

/** Utility class for building error response DTO. */
@RequiredArgsConstructor
public final class ErrorResponseBuilder {

  private static final String ORIGIN = "[credit-card-api] ";
  private static final String HTTP_REQUEST_UUID_KEY = "HTTP-REQUEST-UUID";

  @NonNull private final ApiErrorMessageProvider errorMessageProvider;

  public ErrorResponse build() {

    var error =
        ErrorDetails.builder()
            .requestId(getCurrentRequestId())
            .status(getHttpStatusCode())
            .message(ORIGIN + getErrorMessage())
            .details(getErrorDetails())
            .build();

    return new ErrorResponse(error);
  }

  private static String getCurrentRequestId() {
    return MDC.get(HTTP_REQUEST_UUID_KEY);
  }

  private int getHttpStatusCode() {
    return errorMessageProvider.getHttpStatus().value();
  }

  private String getErrorMessage() {
    return errorMessageProvider.getMessage();
  }

  private String getErrorDetails() {
    return errorMessageProvider.getDetails();
  }
}
