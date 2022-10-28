package com.gmakris.gameshop.gateway.entity.model;

import java.time.LocalDateTime;
import java.util.UUID;

public interface GenericEntity {

    UUID id();

    LocalDateTime createdAt();
}
