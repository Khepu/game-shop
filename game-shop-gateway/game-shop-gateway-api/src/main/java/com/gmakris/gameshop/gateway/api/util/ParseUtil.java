package com.gmakris.gameshop.gateway.api.util;

import java.util.Optional;
import java.util.UUID;
import reactor.core.publisher.Mono;

public final class ParseUtil {

    public static Mono<Integer> toInteger(final Optional<String> stringOption) {
        return Mono.justOrEmpty(stringOption)
            .flatMap(string -> {
                try {
                    return Mono.fromCallable(() -> Integer.parseInt(string, 10));
                } catch (final Exception e) {
                    return Mono.error(e);
                }
            });
    }

    public static Mono<UUID> toUUID(final String pathVariable) {
        return Mono.fromCallable(() -> UUID.fromString(pathVariable));
    }
}
