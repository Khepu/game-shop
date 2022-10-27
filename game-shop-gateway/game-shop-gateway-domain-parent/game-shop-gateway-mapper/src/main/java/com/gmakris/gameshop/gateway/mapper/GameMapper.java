package com.gmakris.gameshop.gateway.mapper;

import com.gmakris.gameshop.gateway.entity.model.Game;
import com.gmakris.gameshop.sdk.dto.GameDto;
import org.springframework.stereotype.Component;

@Component
public class GameMapper implements GenericMapper<Game, GameDto> {

    @Override
    public GameDto to(final Game entity) {
        return new GameDto(
            entity.id(),
            entity.name(),
            entity.createdAt());
    }

    @Override
    public Game from(final GameDto dto) {
        return new Game(
            dto.id(),
            dto.name(),
            dto.createdAt());
    }
}
