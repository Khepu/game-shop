package com.gmakris.gameshop.gateway.api.controller;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import com.gmakris.gameshop.gateway.mapper.UserMapper;
import com.gmakris.gameshop.gateway.service.auth.UserService;
import com.gmakris.gameshop.sdk.dto.NewUserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class UserController implements GenericController {

    private final UserMapper userMapper;
    private final UserService userService;

    public UserController(
        final UserMapper userMapper,
        final UserService userService
    ) {
        this.userMapper = userMapper;
        this.userService = userService;
    }

    private Mono<ServerResponse> register(final ServerRequest serverRequest) {
        return serverRequest.bodyToMono(NewUserDto.class)
            .onErrorMap(throwable -> new RuntimeException("Could not parse credentials!"))
            .map(userMapper::from)
            .flatMap(user -> userService.save(user)
                .doOnError(throwable -> log.error("Could not create user with credentials '{}'!",
                    user,
                    throwable))
                .onErrorMap(throwable -> new RuntimeException(
                    "User with username " + user.username() + " already exists!")))
            .flatMap(__ -> ServerResponse
                .ok()
                .build())
            .onErrorResume(throwable -> ServerResponse
                .status(INTERNAL_SERVER_ERROR)
                .bodyValue(throwable.getMessage()));
    }

    @Override
    public RouterFunction<ServerResponse> routes() {
        return route(POST("/auth/register"), this::register);
    }
}
