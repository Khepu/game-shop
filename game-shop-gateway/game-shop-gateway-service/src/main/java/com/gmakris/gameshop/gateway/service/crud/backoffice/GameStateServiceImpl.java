package com.gmakris.gameshop.gateway.service.crud.backoffice;

import com.gmakris.gameshop.gateway.entity.model.backoffice.GameState;
import com.gmakris.gameshop.gateway.repository.GenericRepository;
import com.gmakris.gameshop.gateway.repository.backoffice.GameStateRepository;
import com.gmakris.gameshop.gateway.service.crud.AbstractCrudService;
import org.springframework.stereotype.Service;

@Service
public class GameStateServiceImpl extends AbstractCrudService<GameState> implements GameStateService {

    private final GameStateRepository repository;

    public GameStateServiceImpl(final GameStateRepository repository) {
        this.repository = repository;
    }

    @Override
    protected GenericRepository<GameState> repository() {
        return repository;
    }
}
