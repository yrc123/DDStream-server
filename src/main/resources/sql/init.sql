create table if not exists CLIENT
(
    id       VARCHAR(64)         not null,
    hostname VARCHAR(32)  not null,
    port     INT          not null,
    nickname VARCHAR(128) not null,
    note     VARCHAR(512),
    constraint CLIENT_PK
        primary key (id)
);

create unique index if not exists HOSTNAME_PORT_UNIQUE
    on CLIENT (hostname, port);

create table if not exists KEY_VALUE_STORE
(
    key   VARCHAR(128) not null,
    value VARCHAR(512) not null,
    constraint KEY_VALUE_STORE_PK
        primary key (key)
);

-- create table if not exists PERMISSION
-- (
--     id              VARCHAR(64)        not null,
--     constraint PERMISSION_PK
--         primary key (id)
-- );
--
-- create unique index if not exists PERMISSION_PERMISSION_NAME_UINDEX
--     on PERMISSION (permission_name);

create table if not exists ROLE
(
    id        VARCHAR(64) not null,
    constraint ROLE_PK
        primary key (id)
);

-- create unique index if not exists ROLE_ROLE_NAME_UINDEX
--     on ROLE (role_name);

create table if not exists USER
(
    id       VARCHAR(64)         not null,
    username VARCHAR(64)  not null,
    password VARCHAR(128) not null,
    nickname VARCHAR(128) not null,
    email    VARCHAR(64),
    constraint USER_PK
        primary key (id)
);

create unique index if not exists USER_USERNAME_UINDEX
    on USER (username);


create table if not exists USER_ROLE
(
    id      VARCHAR(64) not null,
    user_id VARCHAR(64) not null,
    role_id VARCHAR(64) not null,
    constraint USER_ROLE_PK
        primary key (id),
    constraint USER_ROLE_ROLE_ID_FK
        foreign key (role_id) references ROLE
            on update cascade on delete cascade,
    constraint USER_ROLE_USER_ID_FK
        foreign key (user_id) references USER
            on update cascade on delete cascade
);

create unique index if not exists USER_ROLE_ROLE_ID_USER_ID_UINDEX
    on USER_ROLE (user_id, role_id);

create table if not exists ROLE_PERMISSION
(
    id            VARCHAR(64) not null,
    role_id       VARCHAR(64) not null,
    permission_id VARCHAR(64) not null,
    constraint ROLE_PERMISSION_PK
        primary key (id),
--     constraint ROLE_PERMISSION_PERMISSION_ID_FK
--         foreign key (permission_id) references PERMISSION
--             on update cascade on delete cascade,
    constraint ROLE_PERMISSION_ROLE_ID_FK
        foreign key (role_id) references ROLE
            on update cascade on delete cascade
);

create unique index if not exists ROLE_PERMISSION_ROLE_ID_PERMISSION_ID_UINDEX
    on ROLE_PERMISSION (role_id, permission_id);

