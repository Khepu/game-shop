package com.gmakris.gameshop.gateway.entity.model;

import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("orders")
public record Order(
    @Id UUID id,
    UUID userId,
    UUID gameId,
    LocalDateTime createdAt
) implements GenericEntity {
}
