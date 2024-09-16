package com.zand.creditcard.infrastructure.external;

import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

import com.zand.creditcard.domain.exception.ApiException;
import com.zand.creditcard.domain.exception.ApplicationException;
import com.zand.creditcard.domain.model.CreditCard;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class IdentityVerificationService {

  private static final String HEADER_VERSION = "X-API-VERSION";
  private static final String IDENTITY_VERIFICATION_API_VERSION = "v1.0";
  private final WebClient webClient;

  @Value("${api.identity-verification-service.base-url}")
  private String identityVerificationServiceBaseUrl;

  @Retryable(
      maxAttemptsExpression = "${retry.max-attempts}",
      backoff =
          @Backoff(
              delayExpression = "${retry.delay}",
              maxDelayExpression = "${retry.max-delay}",
              multiplierExpression = "${retry.multiplier}"),
      listeners = {"retryLogListener"})
  public void sendCreditCardToIdentityVerification(CreditCard request) {
    var url = getIdentityVerificationServiceUrl();

    try {

      webClient
          .post()
          .uri(url)
          .headers(this::commonHeadersPreparator)
          .body(Mono.just(request), CreditCard.class)
          .retrieve()
          .onStatus(
              HttpStatusCode::isError,
              clientResponse ->
                  clientResponse
                      .bodyToMono(String.class)
                      .flatMap(
                          errorBody ->
                              Mono.error(
                                  new ApplicationException(
                                      "Error during identity verification: " + errorBody))))
          .toBodilessEntity()
          .block();

      log.info("Credit card {} sent to identity verification", request);
    } catch (ApiException e) {
      throw e;
    } catch (Exception e) {
      throw new ApplicationException(
          "Failed to send credit card to identity verification: " + request, e);
    }
  }

  @Recover
  public void sendCreditCardToIdentityVerification(Exception e, CreditCard request) {
    throw new ApplicationException(
        "Failed to send credit card to identity verification " + request, e);
  }

  private String getIdentityVerificationServiceUrl() {

    var uriBuilder = fromHttpUrl(identityVerificationServiceBaseUrl).path("/identity/verification");

    return uriBuilder.toUriString();
  }

  private void commonHeadersPreparator(HttpHeaders headers) {
    headers.add(CONTENT_TYPE, APPLICATION_JSON_VALUE);
    headers.add(ACCEPT, APPLICATION_JSON_VALUE);
    headers.add(HEADER_VERSION, IDENTITY_VERIFICATION_API_VERSION);
  }
}
