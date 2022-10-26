package com.gmakris.gameshop.gateway.service.crud;

import com.gmakris.gameshop.gateway.entity.model.Order;
import com.gmakris.gameshop.gateway.repository.OrderRepository;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;

    public OrderServiceImpl(final OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public Flux<Order> saveAll(final Publisher<Order> orderPublisher) {
        return repository.saveAll(orderPublisher);
    }
}
