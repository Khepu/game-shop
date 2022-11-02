package com.gmakris.gameshop.gateway.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PriceDto(
    UUID id,
    UUID gameId,
    LocalDateTime createdAt,
    BigDecimal value
) implements GenericDto {
}
