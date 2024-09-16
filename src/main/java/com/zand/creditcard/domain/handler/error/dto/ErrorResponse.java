package com.zand.creditcard.domain.handler.error.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Class for error response DTO. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
  private ErrorDetails error;
}
