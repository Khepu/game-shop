package com.gmakris.gameshop.gateway.entity.model;

import java.util.Date;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("games")
public record Game(
    @Id UUID id,
    String name,
    Date createdAt
) implements GenericEntity {
}
