package com.gmakris.gameshop.gateway.entity.model.auth;

import java.util.UUID;
import com.gmakris.gameshop.gateway.entity.model.GenericEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("user_roles")
public record UserRole(
    @Id UUID id,
    UUID userId,
    UUID roleId
) implements GenericEntity {
}
