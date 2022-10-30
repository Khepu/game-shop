package com.gmakris.gameshop.gateway.service.crud.auth;

import com.gmakris.gameshop.gateway.entity.model.auth.User;
import com.gmakris.gameshop.gateway.entity.model.auth.UserRole;
import com.gmakris.gameshop.gateway.repository.auth.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService, ReactiveUserDetailsService {

    private final RoleService roleService;
    private final UserRepository repository;
    private final UserRoleService userRoleService;

    public UserServiceImpl(
        final RoleService roleService,
        final UserRepository repository,
        final UserRoleService userRoleService
    ) {
        this.roleService = roleService;
        this.repository = repository;
        this.userRoleService = userRoleService;
    }

    @Override
    public Mono<User> save(final User user) {
        return repository.save(user)
            .flatMap(persistedUser -> roleService.findByName("USER")
                .map(role -> new UserRole(null, user.id(), role.id(), null))
                .flatMap(userRoleService::save)
                .thenReturn(persistedUser));
    }

    @Override
    public Mono<User> findOne(final String username) {
        return repository.findFirstByUsername(username);
    }

    @Override
    public Mono<UserDetails> findByUsername(final String username) {
        return repository.findFirstByUsername(username)
            .flatMap(user -> userRoleService.findRolesByUser(user)
                .collectList()
                .zipWith(Mono.just(user)))
            .map(rolesAndUser -> new org.springframework.security.core.userdetails.User(
                rolesAndUser.getT2().username(),
                rolesAndUser.getT2().password(),
                rolesAndUser.getT1()
                    .stream()
                    .map(role -> new SimpleGrantedAuthority(role.name()))
                    .toList()));
    }
}
