CREATE TABLE users
(
    id bigint primary key generated by default as identity,
    email varchar(100) not null unique,
    password varchar not null,
    name varchar not null unique,
    age int not null,
    role varchar(20) not null
);

CREATE TABLE articles
(
    id bigint primary key generated by default as identity,
    color varchar(20) not null,
    text varchar not null,
    user_id bigint not null references users(id)
);