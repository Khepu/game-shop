package com.gmakris.gameshop.gateway.service.auth;

import com.gmakris.gameshop.gateway.entity.model.auth.Role;
import reactor.core.publisher.Mono;

public interface RoleService {
    Mono<Role> findByName(String name);
}
