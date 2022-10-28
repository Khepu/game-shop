package com.gmakris.gameshop.gateway.entity.model;

import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("users")
public record User(
    @Id UUID id,
    String username,
    String password,
    LocalDateTime createdAt,
    boolean enabled
) implements GenericEntity {
}
