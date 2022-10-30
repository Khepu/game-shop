package com.gmakris.gameshop.gateway.repository.auth;

import com.gmakris.gameshop.gateway.entity.model.auth.Role;
import com.gmakris.gameshop.gateway.repository.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends GenericRepository<Role> {
}
