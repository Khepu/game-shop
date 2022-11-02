CREATE TABLE unpublished_prices
(
    id         UUID                        NOT NULL DEFAULT gen_random_uuid(),
    game_id    UUID                        NOT NULL,
    user_id    UUID                        NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
    value      DECIMAL                     NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE publications
(
    id                   UUID                        NOT NULL DEFAULT gen_random_uuid(),
    unpublished_price_id UUID                        NOT NULL,
    price_id             UUID                        NOT NULL,
    created_at           TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),

    PRIMARY KEY (id)
);

CREATE TABLE game_states
(
    id          UUID                        NOT NULL DEFAULT gen_random_uuid(),
    game_id     UUID                        NOT NULL,
    user_id     UUID                        NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
    game_status TEXT                        NOT NULL,

    PRIMARY KEY (id)
);

ALTER TABLE unpublished_prices
    ADD CONSTRAINT "fk_unpublished_prices_game_id_games"
        FOREIGN KEY (game_id)
            REFERENCES games (id),
    ADD CONSTRAINT "fk_unpublished_prices_user_id_users"
        FOREIGN KEY (user_id)
            REFERENCES users (id);

ALTER TABLE publications
    ADD CONSTRAINT "fk_publications_unpublished_price_id_unpublished_prices"
        FOREIGN KEY (unpublished_price_id)
            REFERENCES unpublished_prices (id),
    ADD CONSTRAINT "fk_publications_price_id_prices"
        FOREIGN KEY (price_id)
            REFERENCES prices (id),
    ADD CONSTRAINT "unique_publications_unpublished_price_id_price_id"
        UNIQUE (unpublished_price_id, price_id);

ALTER TABLE game_states
    ADD CONSTRAINT "fk_game_states_game_id_games"
        FOREIGN KEY (game_id)
            REFERENCES games (id),
    ADD CONSTRAINT "fk_game_states_user_id_users"
        FOREIGN KEY (user_id)
            REFERENCES users (id);

CREATE INDEX "idx_btree_unpublished_prices_game_id" ON unpublished_prices (game_id);
CREATE INDEX "idx_btree_unpublished_prices_user_id" ON unpublished_prices (user_id);
CREATE INDEX "idx_btree_unpublished_prices_created_at" ON unpublished_prices (created_at);

CREATE INDEX "idx_btree_publications_price_id" ON publications (price_id);
CREATE INDEX "idx_btree_publications_created_at" ON publications (created_at);
CREATE INDEX "idx_btree_publications_unpublished_price_id" ON publications (unpublished_price_id);

CREATE INDEX "idx_btree_game_states_game_id" ON game_states (game_id);
CREATE INDEX "idx_btree_game_states_user_id" ON game_states (user_id);
CREATE INDEX "idx_btree_game_states_created_at" ON game_states (created_at);