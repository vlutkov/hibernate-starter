CREATE TABLE users
(
    id         BIGSERIAL,
    username   VARCHAR(128) UNIQUE,
    firstname  VARCHAR(128),
    lastname   VARCHAR(128),
    birth_date DATE,
    role       VARCHAR(32),
    info       JSONB,
    company_id BIGINT REFERENCES company(id)
);

drop table users;


create table company
(
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(64) NOT NULL UNIQUE
);