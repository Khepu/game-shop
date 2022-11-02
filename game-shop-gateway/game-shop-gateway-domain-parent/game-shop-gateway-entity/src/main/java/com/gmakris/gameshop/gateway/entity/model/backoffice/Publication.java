package com.gmakris.gameshop.gateway.entity.model.backoffice;

import java.time.LocalDateTime;
import java.util.UUID;
import com.gmakris.gameshop.gateway.entity.model.GenericEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("publications")
public record Publication(
    @Id UUID id,
    UUID unpublishedPriceId,
    UUID priceId,
    LocalDateTime createdAt
) implements GenericEntity {
}
