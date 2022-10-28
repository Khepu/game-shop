package com.gmakris.gameshop.gateway.service.auth;

import java.util.List;
import com.gmakris.gameshop.gateway.entity.model.User;
import com.gmakris.gameshop.gateway.repository.UserRepository;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService, ReactiveUserDetailsService {

    private final UserRepository repository;

    public UserServiceImpl(final UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<User> save(final User user) {
        return repository.save(user);
    }

    @Override
    public Mono<UserDetails> findByUsername(final String username) {
        return repository.findFirstByUsername(username)
            .map(user -> new org.springframework.security.core.userdetails.User(
                user.username(),
                user.password(),
                List.of()));
    }
}
