package com.gmakris.gameshop.gateway.api;

import java.util.List;
import com.gmakris.gameshop.gateway.api.controller.GenericController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ApiConfiguration {

    private final Logger log = LoggerFactory.getLogger(ApiConfiguration.class);

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
