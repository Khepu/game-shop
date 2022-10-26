package com.gmakris.gameshop.gateway.entity.projection;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public interface PricedGame {
    UUID gameId();

    UUID priceId();

    Date gameCreatedAt();

    Date priceCreatedAt();

    String name();

    BigDecimal value();
}
