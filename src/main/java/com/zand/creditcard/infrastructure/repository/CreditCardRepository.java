package com.zand.creditcard.infrastructure.repository;

import com.zand.creditcard.domain.model.CreditCard;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, String> {

  Optional<CreditCard> findByEmiratesId(String emiratesId);
}
