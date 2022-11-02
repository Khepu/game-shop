package com.gmakris.gameshop.gateway.dto;

public record PricedGameDto(
    GameDto game,
    PriceDto price
) implements GenericDto {
}
