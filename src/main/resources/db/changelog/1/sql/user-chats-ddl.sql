CREATE TABLE chat
(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(64) NOT NULL UNIQUE
);

CREATE TABLE user_chat
(
    user_id BIGINT,
    chat_id BIGINT,
    PRIMARY KEY (user_id, chat_id)
);

ALTER TABLE users ADD CONSTRAINT pr_key_id PRIMARY KEY (id);

ALTER TABLE user_chat ADD CONSTRAINT pr_key_user_id FOREIGN KEY (user_id) REFERENCES users(id);
ALTER TABLE user_chat ADD CONSTRAINT pr_key_chat_id FOREIGN KEY (chat_id) REFERENCES chat(id);