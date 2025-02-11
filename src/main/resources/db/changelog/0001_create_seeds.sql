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
