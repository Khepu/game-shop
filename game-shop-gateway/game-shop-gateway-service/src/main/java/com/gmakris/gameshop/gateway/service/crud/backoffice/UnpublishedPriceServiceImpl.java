package com.gmakris.gameshop.gateway.service.crud.backoffice;

import java.util.UUID;
import com.gmakris.gameshop.gateway.entity.model.backoffice.UnpublishedPrice;
import com.gmakris.gameshop.gateway.repository.GenericRepository;
import com.gmakris.gameshop.gateway.repository.backoffice.UnpublishedPriceRepository;
import com.gmakris.gameshop.gateway.service.crud.AbstractCrudService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UnpublishedPriceServiceImpl extends AbstractCrudService<UnpublishedPrice> implements UnpublishedPriceService {

    private final UnpublishedPriceRepository repository;

    public UnpublishedPriceServiceImpl(final UnpublishedPriceRepository repository) {
        this.repository = repository;
    }

    @Override
    protected GenericRepository<UnpublishedPrice> repository() {
        return repository;
    }

    @Override
    public Flux<UnpublishedPrice> findAllByUserId(final UUID userId, final int page, final int size) {
        return repository.findAllByUserId(userId, (page - 1) * size, size);
    }

    @Override
    public Mono<UnpublishedPrice> findOneByUserIdAndGameId(final UUID userId, final UUID gameId) {
        return repository.findOneByUserIdAndGameId(userId, gameId);
    }

    @Override
    public Mono<UnpublishedPrice> findOneByIdAndUserId(final UUID id, final UUID userId) {
        return repository.findOneByIdAndUserId(id, userId);
    }
}
