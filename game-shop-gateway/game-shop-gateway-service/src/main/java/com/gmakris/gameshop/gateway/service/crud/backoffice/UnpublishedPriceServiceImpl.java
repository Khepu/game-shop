package com.gmakris.gameshop.gateway.service.crud.backoffice;

import java.util.UUID;
import com.gmakris.gameshop.gateway.entity.model.backoffice.UnpublishedPrice;
import com.gmakris.gameshop.gateway.repository.GenericRepository;
import com.gmakris.gameshop.gateway.repository.backoffice.UnpublishedPriceRepository;
import com.gmakris.gameshop.gateway.service.crud.AbstractCrudService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

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
    public Flux<UnpublishedPrice> findAllByUserId(final UUID userId) {
        return repository.findAllByUserId(userId);
    }
}
