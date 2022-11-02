package com.gmakris.gameshop.gateway.mapper;

import java.time.LocalDateTime;
import com.gmakris.gameshop.gateway.dto.NewUserDto;
import com.gmakris.gameshop.gateway.entity.model.auth.User;
import org.springframework.stereotype.Component;

/**
 * This mapper does not conform to the {@link GenericMapper} interface
 * as it would be unwanted to expose the user's details outside the application's context.
 */
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
