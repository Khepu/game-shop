package com.gmakris.gameshop.gateway.entity.model.auth;

import java.time.LocalDateTime;
import java.util.UUID;
import com.gmakris.gameshop.gateway.entity.model.GenericEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("roles")
public record Role(
    @Id UUID id,
    String name,
    LocalDateTime createdAt
) implements GenericEntity {
}
