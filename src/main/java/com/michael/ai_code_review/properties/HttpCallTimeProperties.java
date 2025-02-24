package com.michael.ai_code_review.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("open-ai")
@Getter
@Setter
public class HttpCallTimeProperties {

    private int timeout;
    private int responseTimeout;
    private int readTimeOut;
    private int writeTimeout;
}
