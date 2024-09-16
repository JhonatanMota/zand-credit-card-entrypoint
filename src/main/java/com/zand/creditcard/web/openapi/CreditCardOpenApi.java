package com.zand.creditcard.web.openapi;

import com.zand.creditcard.domain.model.CreditCardStatus;
import com.zand.creditcard.domain.openapi.StandardErrorApiResponses;
import com.zand.creditcard.web.dto.CreditCardCheckerRequest;
import com.zand.creditcard.web.validators.NotEmptyMultipartFile;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

@SuppressWarnings("unused")
@Tag(name = "Credit Card API", description = "Operations related to credit card endpoints")
public interface CreditCardOpenApi {

  @StandardErrorApiResponses
  @Operation(summary = "Process credit card operation")
  @ApiResponse(responseCode = "200", description = "Ok - Success")
  void processCreditCard(
      @Valid
          @Parameter(
              description = "Credit card checker request body",
              required = true,
              schema = @Schema(type = "string", format = "binary"))
          CreditCardCheckerRequest creditCardRequest,
      @NotEmptyMultipartFile(message = "Bank statement file is required to proceed")
          @Parameter(
              description = "Bank statement file",
              required = true,
              content = @Content(mediaType = "multipart/form-data"),
              schema = @Schema(title = "object", format = "binary"))
          MultipartFile bankStatementFile);

  @StandardErrorApiResponses
  @Operation(summary = "Update credit card status")
  @ApiResponse(responseCode = "200", description = "Ok - Success")
  void updateCreditCardStatus(
      @Parameter(description = "Emirates id", required = true) String emiratesId,
      @Parameter(description = "Credit card status", required = true) CreditCardStatus status);
}
