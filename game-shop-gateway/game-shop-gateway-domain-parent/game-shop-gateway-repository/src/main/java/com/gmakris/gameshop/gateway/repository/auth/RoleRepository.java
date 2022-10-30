package com.gmakris.gameshop.gateway.repository.auth;

import com.gmakris.gameshop.gateway.entity.model.auth.Role;
import com.gmakris.gameshop.gateway.repository.GenericRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface RoleRepository extends GenericRepository<Role> {

    Mono<Role> findRoleByName(String name);
}
