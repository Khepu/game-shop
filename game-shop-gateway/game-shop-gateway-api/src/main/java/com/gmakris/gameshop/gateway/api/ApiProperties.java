package com.gmakris.gameshop.gateway.api;

import java.util.List;
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
    private int maxPageSize = 100;
}
