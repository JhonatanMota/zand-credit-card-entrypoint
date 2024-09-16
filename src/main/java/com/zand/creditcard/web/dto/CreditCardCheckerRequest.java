package com.zand.creditcard.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditCardCheckerRequest {

  @NotBlank private String emiratesId;
  private String name;
  private String mobileNumber;
  private String nationality;
  private String address;
  private BigDecimal income;
  private String employmentDetails;
  @Positive private BigDecimal requestedCreditLimit;
  @JsonIgnore private MultipartFile bankStatementFile;
}
