package com.gmakris.gameshop.gateway.service.auth;

import com.gmakris.gameshop.gateway.entity.model.User;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<User> save(User user);

    Mono<User> findOne(String username);
}
