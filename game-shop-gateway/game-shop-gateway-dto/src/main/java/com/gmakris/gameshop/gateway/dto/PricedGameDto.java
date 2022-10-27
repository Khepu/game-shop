package com.gmakris.gameshop.gateway.dto;

import com.gmakris.gameshop.sdk.dto.GameDto;
import com.gmakris.gameshop.sdk.dto.GenericDto;
import com.gmakris.gameshop.sdk.dto.PriceDto;

public record PricedGameDto(
    GameDto game,
    PriceDto price
) implements GenericDto {
}
