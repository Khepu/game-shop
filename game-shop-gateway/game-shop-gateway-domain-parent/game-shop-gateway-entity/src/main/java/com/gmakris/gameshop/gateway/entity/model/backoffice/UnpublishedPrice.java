package com.gmakris.gameshop.gateway.entity.model.backoffice;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import com.gmakris.gameshop.gateway.entity.model.GenericEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("unpublished_prices")
public record UnpublishedPrice(
    @Id UUID id,
    UUID gameId,
    UUID userId,
    LocalDateTime createdAt,
    BigDecimal value
) implements GenericEntity {
}
