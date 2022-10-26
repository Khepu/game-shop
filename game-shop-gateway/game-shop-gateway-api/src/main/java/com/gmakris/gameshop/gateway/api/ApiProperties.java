package com.gmakris.gameshop.gateway.api;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "api")
public class ApiProperties {

    private List<String> allowedOrigins;
    private List<String> allowedHeaders;

    public ApiProperties(
        final List<String> allowedOrigins,
        final List<String> allowedHeaders
    ) {
        this.allowedOrigins = allowedOrigins;
        this.allowedHeaders = allowedHeaders;
    }

    public List<String> getAllowedOrigins() {
        return allowedOrigins;
    }

    public void setAllowedOrigins(final List<String> allowedOrigins) {
        this.allowedOrigins = allowedOrigins;
    }

    public List<String> getAllowedHeaders() {
        return allowedHeaders;
    }

    public void setAllowedHeaders(final List<String> allowedHeaders) {
        this.allowedHeaders = allowedHeaders;
    }
}
