--liquibase formatted sql
--changeset jessebrands:0001_create_seeds

create extension if not exists "uuid-ossp";

create domain randomizer_version as varchar(32)
    check (value ~*
           '^(0|[1-9][0-9]*)\.(0|[1-9][0-9]*)\.(0|[1-9][0-9]*)-([a-zA-Z0-9]+)-(0|[1-9][0-9]*)\.(0|[1-9][0-9]*)$');

create table seeds
(
    id                 uuid               not null default uuid_generate_v4(),
    version            int                not null default 0,
    seed               varchar(64)        not null,
    preset             varchar(32)        not null,
    randomizer_version randomizer_version not null,
    cooperative        bool               not null default false,
    spoiler_locked     bool               not null default false,
    created_at         timestamp          not null default now(),
    constraint seeds_pk primary key (id)
);

--changeset jessebrands:0001_create_racetime_locks
create domain racetime_race_slug as varchar(32)
    check (value ~* '^[a-z0-9]+-[a-z0-9]+-[0-9]{4}$');

create table racetime_locks
(
    id        uuid               not null default uuid_generate_v4(),
    seed_id   uuid               not null,
    race_slug racetime_race_slug not null,
    constraint racetime_locks_pk primary key (id),
    constraint racetime_locks_fk_seed foreign key (seed_id) references seeds (id)
        on delete cascade on update cascade,
    constraint racetime_locks_uk_race unique (race_slug)
);
