package com.michael.ai_code_review.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("open-ai")
@Getter
@Setter
@ToString
public class OpenAiRequestProperties {

    private String model;
    private Boolean store;
    private String role;
    private String userMessage;
    private String userMessage2;
    private String authorization;
    private String authorizationKey;
    private HttpMethod post;
    private String url;
    private String adminRole;
    private String message;
}
