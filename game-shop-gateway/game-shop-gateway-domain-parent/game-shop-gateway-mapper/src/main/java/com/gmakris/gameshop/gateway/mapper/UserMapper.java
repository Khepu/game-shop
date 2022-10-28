package com.gmakris.gameshop.gateway.mapper;

import java.time.LocalDateTime;
import com.gmakris.gameshop.gateway.entity.model.User;
import com.gmakris.gameshop.sdk.dto.NewUserDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User from(NewUserDto userDto) {
        return new User(
            null,
            userDto.username(),
            userDto.password(),
            LocalDateTime.now(),
            true);
    }
}
