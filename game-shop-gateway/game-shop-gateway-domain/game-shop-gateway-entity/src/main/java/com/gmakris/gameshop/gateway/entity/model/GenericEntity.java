package com.gmakris.gameshop.gateway.entity.model;

import java.util.Date;
import java.util.UUID;

public interface GenericEntity {

    UUID id();

    Date createdAt();
}
