package com.gmakris.gameshop.gateway.dto.backoffice;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import com.gmakris.gameshop.gateway.dto.GenericDto;

public record UnpublishedPriceDto(
    UUID id,
    UUID gameId,
    LocalDateTime createdAt,
    BigDecimal value
) implements GenericDto {
}
