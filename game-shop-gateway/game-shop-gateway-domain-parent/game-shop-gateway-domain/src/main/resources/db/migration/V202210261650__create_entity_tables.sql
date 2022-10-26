CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE games
(
    id         UUID                              DEFAULT gen_random_uuid() NOT NULL,
    name       TEXT                     NOT NULL,

    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),

    price      DECIMAL                  NOT NULL,

    PRIMARY KEY (id, updated_at)
);

CREATE TABLE orders
(
    id                   UUID                              DEFAULT gen_random_uuid() NOT NULL,
    user_id              UUID                     NOT NULL,
    game_id              UUID                     NOT NULL,

    game_last_updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    created_at           TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),

    PRIMARY KEY (id)
);

ALTER TABLE games
    ADD CONSTRAINT "games_non_negative_price"
        CHECK ( price >= 0 );

ALTER TABLE orders
    ADD CONSTRAINT "fk_orders_game_id_games"
        FOREIGN KEY (game_id, game_last_updated_at)
            REFERENCES games (id, updated_at),
    ADD CONSTRAINT "unique_orders_user_id_game_id"
        UNIQUE (user_id, game_id);

CREATE INDEX "idx_btree_orders_user_id" ON orders (user_id);
CREATE INDEX "idx_btree_orders_game_id" ON orders (game_id);

SELECT create_hypertable('games', 'updated_at');
