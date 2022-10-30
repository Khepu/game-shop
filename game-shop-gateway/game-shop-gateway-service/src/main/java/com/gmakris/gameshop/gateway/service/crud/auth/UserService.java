package com.gmakris.gameshop.gateway.service.crud.auth;

import com.gmakris.gameshop.gateway.entity.model.auth.User;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<User> save(User user);

    Mono<User> findOne(String username);
}
