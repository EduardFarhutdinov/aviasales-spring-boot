create table flight
(
    flight_id    bigint generated by default as identity
        constraint flight_pkey
            primary key,
    active       boolean default true,
    company_name varchar(255),
    cost         double precision,
    places       integer,
    destination  varchar(255),
    beginning    varchar(255),
    arrival      timestamp,
    departure    timestamp,
    transport    varchar(255)
);

alter table flight
    owner to postgres;

