package com.zand.creditcard.application.usecase;

import com.zand.creditcard.domain.exception.BadRequestException;
import com.zand.creditcard.domain.model.CreditCard;
import com.zand.creditcard.domain.model.CreditCardStatus;
import com.zand.creditcard.infrastructure.repository.CreditCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateCreditCardStatusUseCase {

  private final CreditCardRepository creditCardRepository;

  public void updateCreditCardStatus(String emiratesId, CreditCardStatus status) {

    CreditCard creditCard = getCreditCardByEmiratesId(emiratesId);

    creditCard.setStatus(status);

    creditCardRepository.save(creditCard);
  }

  private CreditCard getCreditCardByEmiratesId(String emiratesId) {

    return creditCardRepository
        .findByEmiratesId(emiratesId)
        .orElseThrow(
            () -> new BadRequestException("Credit card not found with emirates id: " + emiratesId));
  }
}
