package com.gmakris.gameshop.gateway.entity.model;

import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("cart_items")
public record CartItem(
    @Id UUID id,
    UUID gameId,
    UUID userId,
    LocalDateTime createdAt,
    CartItemOperation operation
) implements GenericEntity {
}
