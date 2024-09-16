package com.zand.creditcard.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zand.creditcard.application.usecase.ProcessCreditCardUseCase;
import com.zand.creditcard.application.usecase.UpdateCreditCardStatusUseCase;
import com.zand.creditcard.domain.model.CreditCardStatus;
import com.zand.creditcard.web.dto.CreditCardCheckerRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CreditCardRestController.class)
class CreditCardRestControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @MockBean private ProcessCreditCardUseCase processCreditCardUseCase;
  @MockBean private UpdateCreditCardStatusUseCase updateCreditCardStatusUseCase;

  private CreditCardCheckerRequest validRequest;

  @BeforeEach
  void setUp() {
    validRequest = CreditCardCheckerRequest.builder().emiratesId("123456789").build();
  }

  @Test
  void givenPostCreditCard_whenValidRequest_shouldReturnOk() throws Exception {

    var bankStatementFile = new MockMultipartFile("bankStatementFile", "mock data".getBytes());

    var json = objectMapper.writeValueAsString(validRequest);

    var creditCardJson =
        new MockMultipartFile(
            "creditCardRequest", null, MediaType.APPLICATION_JSON_VALUE, json.getBytes());

    mockMvc
        .perform(
            multipart("/credit-cards")
                .file(creditCardJson)
                .file(bankStatementFile)
                .header("X-API-VERSION", "v1.0"))
        .andExpect(status().isOk());

    verify(processCreditCardUseCase, times(1)).processCreditCard(any());
  }

  @Test
  void givenPostCreditCard_whenInvalidRequest_shouldReturnBadRequest() throws Exception {

    // given
    var bankStatementFile = new MockMultipartFile("bankStatementFile", "mock data".getBytes());

    var invalidRequest = new CreditCardCheckerRequest();
    var json = objectMapper.writeValueAsString(invalidRequest);

    var creditCardJson =
        new MockMultipartFile(
            "creditCardRequest", null, MediaType.APPLICATION_JSON_VALUE, json.getBytes());

    mockMvc
        .perform(
            multipart("/credit-cards")
                .file(creditCardJson)
                .file(bankStatementFile)
                .header("X-API-VERSION", "v1.0"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void givenPostCreditCard_whenInvalidVersion_shouldReturnNotFound() throws Exception {

    var bankStatementFile = new MockMultipartFile("bankStatementFile", "mock data".getBytes());

    var json = objectMapper.writeValueAsString(validRequest);

    var creditCardJson =
        new MockMultipartFile(
            "creditCardRequest", null, MediaType.APPLICATION_JSON_VALUE, json.getBytes());

    // valid request body, but invalid version
    mockMvc
        .perform(
            multipart("/credit-cards")
                .file(creditCardJson)
                .file(bankStatementFile)
                .header("X-API-VERSION", "v2"))
        .andExpect(status().isNotFound());
  }

  @Test
  void givenPatchUpdateCreditCardStatus_whenValidRequest_shouldReturnOk() throws Exception {

    String url =
        "/credit-cards/" + validRequest.getEmiratesId() + "/status/" + CreditCardStatus.REJECTED;

    mockMvc.perform(patch(url).header("X-API-VERSION", "v1.0")).andExpect(status().isOk());

    verify(updateCreditCardStatusUseCase, times(1)).updateCreditCardStatus(anyString(), any());
  }

  @Test
  void givenPatchUpdateCreditCardStatus_whenInvalidStatus_shouldReturnBadRequest()
      throws Exception {

    String url = "/credit-cards/" + validRequest.getEmiratesId() + "/status/" + "INVALID_STATUS";

    mockMvc.perform(patch(url).header("X-API-VERSION", "v1.0")).andExpect(status().isBadRequest());
  }
}
