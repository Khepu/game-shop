package com.gmakris.gameshop.gateway.api.controller;

import java.security.Principal;
import java.util.UUID;
import com.gmakris.gameshop.gateway.entity.model.auth.User;
import com.gmakris.gameshop.gateway.service.auth.UserService;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

public abstract class AuthenticatedController {

    protected final UserService userService;

    protected AuthenticatedController(final UserService userService) {
        this.userService = userService;
    }

    protected Mono<UUID> getUserId(final ServerRequest serverRequest) {
        return serverRequest.principal()
            .map(Principal::getName)
            .flatMap(userService::findOne)
            .map(User::id);
    }
}
