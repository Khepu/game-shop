package com.gmakris.gameshop.gateway.service.crud.backoffice;

import java.util.UUID;
import com.gmakris.gameshop.gateway.entity.model.Price;
import com.gmakris.gameshop.gateway.entity.model.backoffice.Publication;
import com.gmakris.gameshop.gateway.service.crud.GenericCrudService;
import reactor.core.publisher.Mono;

public interface PublicationService extends GenericCrudService<Publication> {
    Mono<Price> publish(UUID unpublishedPriceId, UUID userId);
}
