package com.gmakris.gameshop.gateway.api;

import java.util.List;
import com.gmakris.gameshop.gateway.api.controller.GenericController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Slf4j
@Configuration
@ComponentScan
@EnableConfigurationProperties(ApiProperties.class)
public class ApiConfiguration {

    @Bean("cors-filter")
    public CorsWebFilter corsFilter(final ApiProperties apiProperties) {
        log.info("Setting cors-filter with allowed-origins '{}' and allowed headers '{}'.",
            apiProperties.getAllowedOrigins(),
            apiProperties.getAllowedHeaders());

        final CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowedOrigins(apiProperties.getAllowedOrigins());
        corsConfiguration.setAllowedHeaders(apiProperties.getAllowedHeaders());
        corsConfiguration.setAllowedMethods(List.of("GET", "POST"));

        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsWebFilter(urlBasedCorsConfigurationSource);
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
