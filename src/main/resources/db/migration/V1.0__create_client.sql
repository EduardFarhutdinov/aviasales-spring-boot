
create table client
(
    client_id       bigint generated by default as identity
        constraint client_pkey
            primary key,
    bonuses         integer default 0,
    fio_client      varchar(255),
    locked          boolean default true,
    number_pass     bigint,
    series_passport bigint,
    total           integer default 0
);

alter table client
    owner to postgres;

