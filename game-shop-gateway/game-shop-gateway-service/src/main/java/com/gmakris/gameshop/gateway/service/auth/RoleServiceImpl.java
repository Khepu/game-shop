package com.gmakris.gameshop.gateway.service.auth;

import com.gmakris.gameshop.gateway.repository.auth.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repository;

    public RoleServiceImpl(final RoleRepository repository) {
        this.repository = repository;
    }
}
