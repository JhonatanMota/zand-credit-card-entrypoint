package com.zand.creditcard.application.usecase;

import com.zand.creditcard.domain.model.CreditCard;
import com.zand.creditcard.infrastructure.external.IdentityVerificationService;
import com.zand.creditcard.infrastructure.external.S3Service;
import com.zand.creditcard.infrastructure.repository.CreditCardRepository;
import com.zand.creditcard.web.dto.CreditCardCheckerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProcessCreditCardUseCase {

  private final CreditCardRepository creditCardRepository;
  private final IdentityVerificationService identityVerificationService;
  private final S3Service s3Service;

  public void processCreditCard(CreditCardCheckerRequest request) {

    String bankStatementPath = s3Service.uploadFile(request.getBankStatementFile());

    CreditCard creditCard = creditCardRepository.save(buildCreditCard(request, bankStatementPath));

    identityVerificationService.sendCreditCardToIdentityVerification(creditCard);
  }

  private CreditCard buildCreditCard(CreditCardCheckerRequest request, String bankStatementPath) {
    CreditCard creditCard = CreditCard.from(request);
    creditCard.setBankStatementFilePath(bankStatementPath);

    return creditCard;
  }
}
