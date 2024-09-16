package com.zand.creditcard.web;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

import com.zand.creditcard.application.usecase.ProcessCreditCardUseCase;
import com.zand.creditcard.application.usecase.UpdateCreditCardStatusUseCase;
import com.zand.creditcard.domain.model.CreditCardStatus;
import com.zand.creditcard.web.dto.CreditCardCheckerRequest;
import com.zand.creditcard.web.openapi.CreditCardOpenApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(value = "/credit-cards")
public class CreditCardRestController implements CreditCardOpenApi {

  private final ProcessCreditCardUseCase processCreditCardUseCase;
  private final UpdateCreditCardStatusUseCase updateCreditCardStatusUseCase;

  @Override
  @ResponseStatus(value = OK)
  @PostMapping(
      headers = "X-API-VERSION=v1.0",
      consumes = {MULTIPART_FORM_DATA_VALUE})
  public void processCreditCard(
      @RequestPart("creditCardRequest") CreditCardCheckerRequest request,
      @RequestPart("bankStatementFile") MultipartFile bankStatementFile) {

    request.setBankStatementFile(bankStatementFile);

    log.info("Received request: {}", request);

    processCreditCardUseCase.processCreditCard(request);
  }

  @ResponseStatus(value = OK)
  @PatchMapping(value = "/{emiratesId}/status/{status}", headers = "X-API-VERSION=v1.0")
  @Override
  public void updateCreditCardStatus(
      @PathVariable String emiratesId, @PathVariable CreditCardStatus status) {

    updateCreditCardStatusUseCase.updateCreditCardStatus(emiratesId, status);
    log.info(
        "Received request to update credit card status for emiratesId: {}, status: {}",
        emiratesId,
        status);
  }
}
