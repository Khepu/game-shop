package com.gmakris.gameshop.gateway.service.crud.backoffice;

import com.gmakris.gameshop.gateway.entity.model.backoffice.Publication;
import com.gmakris.gameshop.gateway.repository.GenericRepository;
import com.gmakris.gameshop.gateway.repository.backoffice.PublicationRepository;
import com.gmakris.gameshop.gateway.service.crud.AbstractCrudService;
import org.springframework.stereotype.Service;

@Service
public class PublicationServiceImpl extends AbstractCrudService<Publication> implements PublicationService {

    private final PublicationRepository repository;

    public PublicationServiceImpl(final PublicationRepository repository) {
        this.repository = repository;
    }

    @Override
    protected GenericRepository<Publication> repository() {
        return repository;
    }
}
