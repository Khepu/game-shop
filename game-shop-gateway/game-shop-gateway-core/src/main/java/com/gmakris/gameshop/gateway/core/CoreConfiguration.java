package com.gmakris.gameshop.gateway.core;

import com.gmakris.gameshop.gateway.api.ApiConfiguration;
import com.gmakris.gameshop.gateway.domain.DomainConfiguration;
import com.gmakris.gameshop.gateway.mapper.MapperConfiguration;
import com.gmakris.gameshop.gateway.repository.RepositoryConfiguration;
import com.gmakris.gameshop.gateway.service.ServiceConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
    ApiConfiguration.class,
    MapperConfiguration.class,
    DomainConfiguration.class,
    ServiceConfiguration.class,
    RepositoryConfiguration.class
})
public class CoreConfiguration {
}
