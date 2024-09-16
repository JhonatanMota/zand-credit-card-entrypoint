package com.zand.creditcard.domain.handler.error.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Class for error details DTO. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetails {
  private String requestId;
  private Integer status;
  private String message;
  private String details;
}
