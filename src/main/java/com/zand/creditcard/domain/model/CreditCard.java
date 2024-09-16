package com.zand.creditcard.domain.model;

import com.zand.creditcard.web.dto.CreditCardCheckerRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreditCard implements Serializable {

  @Id private String emiratesId;
  private String name;
  private String mobileNumber;
  private String nationality;
  private String address;
  private BigDecimal income;
  private String employmentDetails;
  private BigDecimal requestedCreditLimit;
  @Setter private String bankStatementFilePath;

  private boolean identityVerified;
  private boolean employmentVerified;
  private boolean complianceCheckPassed;
  private Double riskEvaluationScore;
  private Double behavioralAnalysisScore;

  @Setter private CreditCardStatus status;

  public static CreditCard from(CreditCardCheckerRequest request) {
    return CreditCard.builder()
        .emiratesId(request.getEmiratesId())
        .name(request.getName())
        .mobileNumber(request.getMobileNumber())
        .nationality(request.getNationality())
        .address(request.getAddress())
        .income(request.getIncome())
        .employmentDetails(request.getEmploymentDetails())
        .requestedCreditLimit(request.getRequestedCreditLimit())
        .identityVerified(false)
        .employmentVerified(false)
        .complianceCheckPassed(false)
        .build();
  }
}
