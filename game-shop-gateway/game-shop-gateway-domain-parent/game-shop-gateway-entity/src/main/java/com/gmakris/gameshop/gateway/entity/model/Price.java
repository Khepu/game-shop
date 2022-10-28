package com.gmakris.gameshop.gateway.entity.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("prices")
public record Price(
    @Id UUID id,
    UUID gameId,
    LocalDateTime createdAt,
    BigDecimal value
) implements GenericEntity {
}
