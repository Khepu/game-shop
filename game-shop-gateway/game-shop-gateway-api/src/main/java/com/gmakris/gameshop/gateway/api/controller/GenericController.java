package com.gmakris.gameshop.gateway.api.controller;

import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

public interface GenericController {

    RouterFunction<ServerResponse> routes();
}
