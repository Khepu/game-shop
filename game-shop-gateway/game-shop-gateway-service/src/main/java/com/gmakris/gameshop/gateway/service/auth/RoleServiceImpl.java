package com.gmakris.gameshop.gateway.service.auth;

import com.gmakris.gameshop.gateway.entity.model.auth.Role;
import com.gmakris.gameshop.gateway.repository.auth.RoleRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repository;

    public RoleServiceImpl(final RoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Role> findByName(final String name) {
        return repository.findRoleByName(name);
    }
}
