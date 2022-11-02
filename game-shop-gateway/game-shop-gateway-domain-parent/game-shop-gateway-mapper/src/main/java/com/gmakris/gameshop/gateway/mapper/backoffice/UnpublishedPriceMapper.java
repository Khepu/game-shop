package com.gmakris.gameshop.gateway.mapper.backoffice;

import com.gmakris.gameshop.gateway.dto.UnpublishedPriceDto;
import com.gmakris.gameshop.gateway.entity.model.backoffice.UnpublishedPrice;
import com.gmakris.gameshop.gateway.mapper.GenericMapper;
import org.springframework.stereotype.Component;

@Component
public class UnpublishedPriceMapper implements GenericMapper<UnpublishedPrice, UnpublishedPriceDto> {
    @Override
    public UnpublishedPriceDto to(final UnpublishedPrice entity) {
        return new UnpublishedPriceDto(
            entity.id(),
            entity.gameId(),
            entity.createdAt(),
            entity.value());
    }

    @Override
    public UnpublishedPrice from(final UnpublishedPriceDto dto) {
        return new UnpublishedPrice(
            dto.id(),
            dto.gameId(),
            null,
            dto.createdAt(),
            dto.value());
    }
}
