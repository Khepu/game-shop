package com.gmakris.gameshop.gateway.entity.model;

import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("games")
public record Game(
    @Id UUID id,
    String name,
    LocalDateTime createdAt
) implements GenericEntity {
}
