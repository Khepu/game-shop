package com.gmakris.gameshop.gateway.api;

import static java.util.Objects.requireNonNull;

import java.util.List;
import javax.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "api")
public class ApiProperties {

    private List<String> allowedOrigins;
    private List<String> allowedHeaders;

    /**
     * Default pagination size.
     */
    private int defaultPageSize = 20;

    /**
     * Maximum page size allowed.
     */
    private int maxPageSize = 100;

    @PostConstruct
    public void validOrThrow() {
        requireNonNull(allowedOrigins, "api.allowed-origins cannot be null!");
        requireNonNull(allowedHeaders, "api.allowed-headers cannot be null!");

        if (defaultPageSize <= 0) {
            throw new RuntimeException("api.default-page-size has to be > 0!");
        }

        if (maxPageSize <= defaultPageSize) {
            throw new RuntimeException("api.max-page-size has to be > api.default-page-size!");
        }
    }
}
