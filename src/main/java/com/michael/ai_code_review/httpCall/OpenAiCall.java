package com.michael.ai_code_review.httpCall;

import com.michael.ai_code_review.properties.HttpCallTimeProperties;
import com.michael.ai_code_review.properties.OpenAiRequestProperties;
import com.michael.ai_code_review.dto.request.openAiRequestDto.OpenAiRequest;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Component
@Slf4j
@RequiredArgsConstructor
public class OpenAiCall {

    private final HttpCallTimeProperties httpCallTimeProperties;

    public HashMap<String, Object> openAiCall(OpenAiRequest openAiRequest, OpenAiRequestProperties openAiRequestProperties) {
        log.info("open ai call...");


        HttpClient httpClient = httpMethod();

        WebClient client = webClientMethod(httpClient);

        WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = client.method(openAiRequestProperties.getPost());
        WebClient.RequestBodySpec bodySpec = uriSpec.uri(openAiRequestProperties.getUrl());
        WebClient.RequestHeadersSpec<?> headersSpec = bodySpec.bodyValue(openAiRequest);
        headersSpec.header( "Content-Type","application/json");
        headersSpec.header(openAiRequestProperties.getAuthorizationKey(), openAiRequestProperties.getAuthorization());
        WebClient.ResponseSpec responseSpec = headersSpec.header(
                        HttpHeaders.CONTENT_TYPE)
                .accept(MediaType.APPLICATION_JSON)
                .ifNoneMatch("*")
                .retrieve();

        Mono<Object> messageResponse = headersSpec.exchangeToMono(response -> response.bodyToMono(Object.class));
        Object message = messageResponse.block();
        return (HashMap<String, Object>) message;
    }

    private WebClient webClientMethod(HttpClient httpClient) {
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    private HttpClient httpMethod() {
        return HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, httpCallTimeProperties.getTimeout())
                .responseTimeout(Duration.ofSeconds(httpCallTimeProperties.getResponseTimeout()))
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(httpCallTimeProperties.getReadTimeOut(), TimeUnit.SECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(httpCallTimeProperties.getWriteTimeout(), TimeUnit.SECONDS)));
    }
}
