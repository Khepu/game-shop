package com.gmakris.gameshop.gateway.repository;

import java.util.UUID;
import com.gmakris.gameshop.gateway.entity.model.GenericEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericRepository<Entity extends GenericEntity> extends R2dbcRepository<Entity, UUID> {
}
