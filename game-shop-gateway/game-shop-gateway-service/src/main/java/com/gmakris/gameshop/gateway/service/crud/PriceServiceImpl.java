package com.gmakris.gameshop.gateway.service.crud;

import com.gmakris.gameshop.gateway.entity.model.Price;
import com.gmakris.gameshop.gateway.repository.PriceRepository;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class PriceServiceImpl implements PriceService {

    private final PriceRepository repository;

    public PriceServiceImpl(final PriceRepository repository) {
        this.repository = repository;
    }

    @Override
    public Flux<Price> saveAll(final Publisher<Price> pricePublisher) {
        return repository.saveAll(pricePublisher);
    }
}