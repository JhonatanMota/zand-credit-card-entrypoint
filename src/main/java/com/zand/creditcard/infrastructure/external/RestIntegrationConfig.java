package com.zand.creditcard.infrastructure.external;

import static io.netty.channel.ChannelOption.CONNECT_TIMEOUT_MILLIS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

/**
 * Class for API integrations config. Contains configuration of HTTP connection timeouts, API URLs
 * and so on. Also defines REST template beans and factories.
 */
@Configuration
public class RestIntegrationConfig {

  @Value("${api.timeout:5000}")
  private Integer timeoutMillis;

  @Bean
  public WebClient webClient() {

    var httpClient =
        HttpClient.create()
            .option(CONNECT_TIMEOUT_MILLIS, timeoutMillis)
            .responseTimeout(Duration.ofMillis(timeoutMillis))
            .doOnConnected(
                conn ->
                    conn.addHandlerLast(new ReadTimeoutHandler(timeoutMillis, MILLISECONDS))
                        .addHandlerLast(new WriteTimeoutHandler(timeoutMillis, MILLISECONDS)));

    var connector = new ReactorClientHttpConnector(httpClient);

    return WebClient.builder()
        .clientConnector(connector)
        .defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
        .build();
  }
}
