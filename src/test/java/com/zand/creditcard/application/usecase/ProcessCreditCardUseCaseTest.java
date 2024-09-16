package com.zand.creditcard.application.usecase;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.zand.creditcard.domain.model.CreditCard;
import com.zand.creditcard.infrastructure.external.IdentityVerificationService;
import com.zand.creditcard.infrastructure.external.S3Service;
import com.zand.creditcard.infrastructure.repository.CreditCardRepository;
import com.zand.creditcard.web.dto.CreditCardCheckerRequest;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ProcessCreditCardUseCaseTest {
  @Mock private CreditCardRepository creditCardRepository;

  @InjectMocks private ProcessCreditCardUseCase processCreditCardUseCase;

  @Mock private IdentityVerificationService identityVerificationService;
  @Mock private S3Service s3Service;

  private CreditCardCheckerRequest validRequest;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    validRequest =
        CreditCardCheckerRequest.builder()
            .emiratesId("784-1987-1234567-1")
            .name("John Doe")
            .mobileNumber("+971501234567")
            .nationality("UAE")
            .address("1234 Sheikh Zayed Road, Dubai")
            .income(new BigDecimal("15000.00"))
            .employmentDetails("Software Engineer at ABC Tech")
            .requestedCreditLimit(new BigDecimal("20000.00"))
            .build();
  }

  @Test
  void givenValidRequest_whenProcessCreditCard_thenSavesCreditCardAndSendsToSqs() {
    // Arrange
    CreditCard mockCreditCard = mock(CreditCard.class);

    when(creditCardRepository.save(any(CreditCard.class))).thenReturn(mockCreditCard);

    // Act
    processCreditCardUseCase.processCreditCard(validRequest);

    // Assert
    verify(creditCardRepository, times(1)).save(any(CreditCard.class));
    verify(identityVerificationService, times(1))
        .sendCreditCardToIdentityVerification(mockCreditCard);
    verify(s3Service, times(1)).uploadFile(any());
  }
}
