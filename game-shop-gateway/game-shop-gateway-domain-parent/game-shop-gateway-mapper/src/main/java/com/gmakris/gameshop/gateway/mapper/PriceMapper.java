package com.gmakris.gameshop.gateway.mapper;

import com.gmakris.gameshop.gateway.dto.PriceDto;
import com.gmakris.gameshop.gateway.entity.model.Price;
import org.springframework.stereotype.Component;

@Component
public class PriceMapper implements GenericMapper<Price, PriceDto> {

    @Override
    public PriceDto to(final Price entity) {
        return new PriceDto(
            entity.id(),
            entity.gameId(),
            entity.createdAt(),
            entity.value());
    }

    @Override
    public Price from(final PriceDto dto) {
        return new Price(
            dto.id(),
            dto.gameId(),
            dto.createdAt(),
            dto.value());
    }
}
