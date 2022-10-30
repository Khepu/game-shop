package com.gmakris.gameshop.gateway.service.crud.auth;

import com.gmakris.gameshop.gateway.entity.model.auth.Role;
import com.gmakris.gameshop.gateway.entity.model.auth.User;
import com.gmakris.gameshop.gateway.entity.model.auth.UserRole;
import com.gmakris.gameshop.gateway.service.crud.GenericCrudService;
import reactor.core.publisher.Flux;

public interface UserRoleService extends GenericCrudService<UserRole> {
    Flux<Role> findRolesByUser(User user);
}
