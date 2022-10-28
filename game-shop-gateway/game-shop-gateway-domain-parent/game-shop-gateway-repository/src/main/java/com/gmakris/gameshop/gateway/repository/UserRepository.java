package com.gmakris.gameshop.gateway.repository;

import com.gmakris.gameshop.gateway.entity.model.User;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends GenericRepository<User> {

    Mono<User> findFirstByUsername(String username);
}
