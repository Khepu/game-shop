package com.gmakris.gameshop.gateway.service.crud;

import com.gmakris.gameshop.gateway.entity.model.Price;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

public interface PriceService {
    Flux<Price> saveAll(Publisher<Price> pricePublisher);
}
