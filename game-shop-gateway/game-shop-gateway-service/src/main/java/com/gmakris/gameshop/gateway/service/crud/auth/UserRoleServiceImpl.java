package com.gmakris.gameshop.gateway.service.crud.auth;

import com.gmakris.gameshop.gateway.entity.model.auth.Role;
import com.gmakris.gameshop.gateway.entity.model.auth.User;
import com.gmakris.gameshop.gateway.entity.model.auth.UserRole;
import com.gmakris.gameshop.gateway.repository.auth.UserRoleRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository repository;

    public UserRoleServiceImpl(final UserRoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<UserRole> save(final UserRole userRole) {
        return repository.save(userRole);
    }

    @Override
    public Flux<Role> findRolesByUser(final User user) {
        return repository.findRolesByUserId(user.id());
    }
}
