CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE games
(
    id              UUID                        NOT NULL DEFAULT gen_random_uuid(),
    name            TEXT                        NOT NULL,
    created_at      TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
    name_embeddings TSVECTOR                    NOT NULL GENERATED ALWAYS AS (to_tsvector('english', name)) STORED,

    PRIMARY KEY (id)
);

CREATE TABLE prices
(
    id         UUID                        NOT NULL DEFAULT gen_random_uuid(),
    game_id    UUID                        NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
    value      DECIMAL                     NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE orders
(
    id         UUID                        NOT NULL DEFAULT gen_random_uuid(),
    user_id    UUID                        NOT NULL,
    game_id    UUID                        NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),

    PRIMARY KEY (id)
);

CREATE TABLE users
(
    id         UUID                        NOT NULL DEFAULT gen_random_uuid(),
    username   TEXT                        NOT NULL,
    password   TEXT                        NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
    enabled    BOOLEAN                     NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE cart_items
(
    id         UUID                        NOT NULL DEFAULT gen_random_uuid(),
    game_id    UUID                        NOT NULL,
    user_id    UUID                        NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
    operation  TEXT                        NOT NULL,

    PRIMARY KEY (id)
);

ALTER TABLE prices
    ADD CONSTRAINT "fk_prices_game_id_games"
        FOREIGN KEY (game_id)
            REFERENCES games (id),
    ADD CONSTRAINT "unique_prices_game_id_created_at"
        UNIQUE (game_id, created_at),
    ADD CONSTRAINT "prices_non_negative_value"
        CHECK ( value >= 0 );

ALTER TABLE orders
    ADD CONSTRAINT "fk_orders_users_id_users"
        FOREIGN KEY (user_id)
            REFERENCES users (id),
    ADD CONSTRAINT "fk_orders_game_id_games"
        FOREIGN KEY (game_id)
            REFERENCES games (id),
    ADD CONSTRAINT "unique_orders_user_id_game_id"
        UNIQUE (user_id, game_id);

ALTER TABLE cart_items
    ADD CONSTRAINT "fk_cart_items_game_id_games"
        FOREIGN KEY (game_id)
            REFERENCES games (id),
    ADD CONSTRAINT "fk_cart_items_user_id_users"
        FOREIGN KEY (user_id)
            REFERENCES users (id);

ALTER TABLE users
    ADD CONSTRAINT "unique_users_username"
        UNIQUE (username);

CREATE INDEX "idx_btree_games_created_at" ON games (created_at DESC);
CREATE INDEX "idx_gin_games_name" ON games USING gin (name_embeddings);

CREATE INDEX "idx_btree_prices_game_id" ON prices (game_id);
CREATE INDEX "idx_btree_prices_created_at" ON prices (created_at DESC);

CREATE INDEX "idx_btree_orders_user_id" ON orders (user_id);
CREATE INDEX "idx_btree_orders_game_id" ON orders (game_id);

CREATE INDEX "idx_btree_users_username" ON users (username);

CREATE INDEX "idx_btree_cart_items_game_id" ON cart_items (game_id);
CREATE INDEX "idx_btree_cart_items_user_id" ON cart_items (user_id);
CREATE INDEX "idx_btree_cart_items_created_at" ON cart_items (created_at DESC);
