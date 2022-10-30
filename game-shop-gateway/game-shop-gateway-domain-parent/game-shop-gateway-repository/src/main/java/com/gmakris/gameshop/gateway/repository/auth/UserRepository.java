package com.gmakris.gameshop.gateway.repository.auth;

import com.gmakris.gameshop.gateway.entity.model.auth.User;
import com.gmakris.gameshop.gateway.repository.GenericRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends GenericRepository<User> {

    Mono<User> findFirstByUsername(String username);
}
