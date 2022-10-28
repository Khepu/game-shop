package com.gmakris.gameshop.sdk.dto;

public record PricedGameDto(
    GameDto game,
    PriceDto price
) implements GenericDto {
}
