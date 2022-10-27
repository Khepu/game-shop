package com.gmakris.gameshop.gateway.mapper;

import com.gmakris.gameshop.gateway.entity.model.GenericEntity;
import com.gmakris.gameshop.sdk.dto.GenericDto;

public interface GenericMapper<Entity extends GenericEntity, Dto extends GenericDto> {

    Dto to(Entity entity);

    Entity from(Dto dto);
}
