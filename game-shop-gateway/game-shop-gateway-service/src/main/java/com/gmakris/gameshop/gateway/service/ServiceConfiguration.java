package com.gmakris.gameshop.gateway.service;

import com.gmakris.gameshop.gateway.repository.RepositoryConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({RepositoryConfiguration.class})
@Configuration
@ComponentScan
public class ServiceConfiguration {
}
