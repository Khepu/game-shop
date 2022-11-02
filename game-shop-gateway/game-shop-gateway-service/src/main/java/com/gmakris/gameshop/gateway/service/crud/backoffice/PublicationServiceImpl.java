package com.gmakris.gameshop.gateway.service.crud.backoffice;

import java.util.UUID;
import com.gmakris.gameshop.gateway.entity.model.Price;
import com.gmakris.gameshop.gateway.entity.model.backoffice.Publication;
import com.gmakris.gameshop.gateway.repository.GenericRepository;
import com.gmakris.gameshop.gateway.repository.backoffice.PublicationRepository;
import com.gmakris.gameshop.gateway.service.crud.AbstractCrudService;
import com.gmakris.gameshop.gateway.service.crud.PriceService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PublicationServiceImpl extends AbstractCrudService<Publication> implements PublicationService {

    private final PriceService priceService;
    private final PublicationRepository repository;
    private final UnpublishedPriceService unpublishedPriceService;

    public PublicationServiceImpl(
        final PriceService priceService,
        final PublicationRepository repository,
        final UnpublishedPriceService unpublishedPriceService
    ) {
        this.priceService = priceService;
        this.repository = repository;
        this.unpublishedPriceService = unpublishedPriceService;
    }

    @Override
    protected GenericRepository<Publication> repository() {
        return repository;
    }

    @Override
    public Mono<Price> publish(final UUID unpublishedPriceId, final UUID userId) {
        return unpublishedPriceService.findOneByIdAndUserId(unpublishedPriceId, userId)
            .map(unpublishedPrice -> new Price(
                null,
                unpublishedPrice.gameId(),
                null,
                unpublishedPrice.value()))
            .flatMap(priceService::save)
            .flatMap(publishedPrice -> save(
                new Publication(
                    null,
                    unpublishedPriceId,
                    publishedPrice.id(),
                    null))
                .thenReturn(publishedPrice));
    }
}
