package com.gmakris.gameshop.gateway.api;

import java.util.List;
import com.gmakris.gameshop.gateway.api.controller.GenericController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@EnableConfigurationProperties(ApiProperties.class)
public class ApiConfiguration {

    private final Logger log = LoggerFactory.getLogger(ApiConfiguration.class);

    @Bean("cors-filter")
    public CorsFilter corsFilter(final ApiProperties apiProperties) {
        log.info("Setting cors-filter with allowed-origins '{}' and allowed headers '{}'.",
            apiProperties.getAllowedOrigins(),
            apiProperties.getAllowedHeaders());

        final CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowedOrigins(apiProperties.getAllowedOrigins());
        corsConfiguration.setAllowedHeaders(apiProperties.getAllowedHeaders());
        corsConfiguration.setAllowedMethods(List.of("GET", "POST"));

        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsFilter(urlBasedCorsConfigurationSource);
    }

    @Bean("routes")
    public RouterFunction<ServerResponse> routes(final List<GenericController> controllers) {
        log.info("Registering {} controllers.", controllers.size());

        return controllers
            .stream()
            .map(GenericController::routes)
            .reduce(RouterFunction::and)
            .orElseThrow(() -> new RuntimeException("Unable to compose API routes!"));
    }
}
