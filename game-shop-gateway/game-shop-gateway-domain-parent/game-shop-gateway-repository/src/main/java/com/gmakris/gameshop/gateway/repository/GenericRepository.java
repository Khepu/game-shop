package com.gmakris.gameshop.gateway.repository;

import java.util.UUID;
import com.gmakris.gameshop.gateway.entity.model.GenericEntity;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

@NoRepositoryBean
public interface GenericRepository<Entity extends GenericEntity> extends ReactiveCrudRepository<Entity, UUID> {
}
