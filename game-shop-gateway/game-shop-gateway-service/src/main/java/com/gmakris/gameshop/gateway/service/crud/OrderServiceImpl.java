package com.gmakris.gameshop.gateway.service.crud;

import com.gmakris.gameshop.gateway.entity.model.Order;
import com.gmakris.gameshop.gateway.repository.GenericRepository;
import com.gmakris.gameshop.gateway.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends AbstractCrudService<Order> implements OrderService {

    private final OrderRepository repository;

    public OrderServiceImpl(final OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    protected GenericRepository<Order> repository() {
        return repository;
    }
}
