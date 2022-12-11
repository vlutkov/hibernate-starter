CREATE TABLE chat
(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(64) NOT NULL UNIQUE
);

CREATE TABLE users_chat
(
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    chat_id BIGINT NOT NULL REFERENCES chat(id),
    created_at TIMESTAMP NOT NULL,
    created_by VARCHAR(20) NOT NULL
);
