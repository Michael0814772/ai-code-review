package com.michael.ai_code_review.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("jwt-token")
@Getter
@Setter
@ToString
public class JwtWebTokenProperties {

    private String secretKey;
    private Long expirationTime;
}
