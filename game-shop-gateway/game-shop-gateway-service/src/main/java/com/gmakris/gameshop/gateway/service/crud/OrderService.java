package com.gmakris.gameshop.gateway.service.crud;

import com.gmakris.gameshop.gateway.entity.model.Order;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

public interface OrderService {

    Flux<Order> saveAll(Publisher<Order> orderPublisher);
}
