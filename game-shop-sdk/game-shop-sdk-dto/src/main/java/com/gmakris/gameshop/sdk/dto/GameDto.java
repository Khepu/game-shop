package com.gmakris.gameshop.sdk.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record GameDto(
    UUID id,
    String name,
    LocalDateTime createdAt
) implements GenericDto {
}
