package com.gmakris.gameshop.gateway.service.auth;

import com.gmakris.gameshop.gateway.entity.model.auth.Role;
import com.gmakris.gameshop.gateway.entity.model.auth.User;
import com.gmakris.gameshop.gateway.entity.model.auth.UserRole;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRoleService {
    Mono<UserRole> save(UserRole userRole);

    Flux<Role> findRolesByUser(User user);
}
