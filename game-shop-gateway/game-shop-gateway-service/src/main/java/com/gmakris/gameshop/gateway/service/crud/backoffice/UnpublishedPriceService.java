package com.gmakris.gameshop.gateway.service.crud.backoffice;

import java.util.UUID;
import com.gmakris.gameshop.gateway.entity.model.backoffice.UnpublishedPrice;
import com.gmakris.gameshop.gateway.service.crud.GenericCrudService;
import reactor.core.publisher.Flux;

public interface UnpublishedPriceService extends GenericCrudService<UnpublishedPrice> {
    Flux<UnpublishedPrice> findAllByUserId(UUID userId);
}
