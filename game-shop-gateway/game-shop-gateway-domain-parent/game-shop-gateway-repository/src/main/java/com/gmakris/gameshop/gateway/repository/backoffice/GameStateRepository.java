package com.gmakris.gameshop.gateway.repository.backoffice;

import com.gmakris.gameshop.gateway.entity.model.backoffice.GameState;
import com.gmakris.gameshop.gateway.repository.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameStateRepository extends GenericRepository<GameState> {
}
