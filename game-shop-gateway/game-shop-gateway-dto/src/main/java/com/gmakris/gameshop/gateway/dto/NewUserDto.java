package com.gmakris.gameshop.gateway.dto;

public record NewUserDto(
    String username,
    String password
) implements GenericDto {}
