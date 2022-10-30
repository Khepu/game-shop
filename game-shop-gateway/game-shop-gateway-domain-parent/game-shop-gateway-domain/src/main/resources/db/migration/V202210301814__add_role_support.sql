CREATE TABLE roles
(
    id         UUID                        NOT NULL DEFAULT gen_random_uuid(),
    name       TEXT                        NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),

    PRIMARY KEY (id)
);

CREATE TABLE user_roles
(
    id      UUID NOT NULL DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL,
    role_id UUID NOT NULL,

    PRIMARY KEY (id)
);

ALTER TABLE roles
    ADD CONSTRAINT "unique_roles_name"
        UNIQUE (name);

ALTER TABLE user_roles
    ADD CONSTRAINT "fk_user_roles_user_id_users"
        FOREIGN KEY (user_id)
            REFERENCES users (id),
    ADD CONSTRAINT "fk_user_roles_role_id_roles"
        FOREIGN KEY (role_id)
            REFERENCES roles (id),
    ADD CONSTRAINT "unique_user_roles_user_id_role_id"
        UNIQUE (user_id, role_id);

-- default roles
INSERT INTO roles (name)
VALUES ('USER'),
       ('ADMIN');