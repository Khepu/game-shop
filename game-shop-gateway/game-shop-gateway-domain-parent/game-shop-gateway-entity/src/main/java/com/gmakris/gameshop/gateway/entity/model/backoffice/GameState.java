package com.gmakris.gameshop.gateway.entity.model.backoffice;

import java.time.LocalDateTime;
import java.util.UUID;
import com.gmakris.gameshop.gateway.entity.model.GenericEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("game_states")
public record GameState(
    @Id UUID id,
    UUID gameId,
    UUID userId,
    LocalDateTime createdAt,
    GameStatus gameStatus
) implements GenericEntity {
}
