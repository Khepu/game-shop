package com.gmakris.gameshop.gateway.service.crud.auth;

import com.gmakris.gameshop.gateway.entity.model.auth.Role;
import com.gmakris.gameshop.gateway.entity.model.auth.User;
import com.gmakris.gameshop.gateway.entity.model.auth.UserRole;
import com.gmakris.gameshop.gateway.repository.GenericRepository;
import com.gmakris.gameshop.gateway.repository.auth.UserRoleRepository;
import com.gmakris.gameshop.gateway.service.crud.AbstractCrudService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class UserRoleServiceImpl extends AbstractCrudService<UserRole> implements UserRoleService {

    private final UserRoleRepository repository;

    public UserRoleServiceImpl(final UserRoleRepository repository) {
        this.repository = repository;
    }

    @Override
    protected GenericRepository<UserRole> repository() {
        return repository;
    }

    @Override
    public Flux<Role> findRolesByUser(final User user) {
        return repository.findRolesByUserId(user.id());
    }
}
