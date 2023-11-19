--liquibase formatted sql

--changeset jessebrands:add_seasons
drop table if exists seasons;

create table if not exists seasons
(
    id          bigserial primary key,
    ordinal     int          not null,
    preset      varchar(64)  not null default 'Triforce Blitz',
    message_key varchar(255) not null default 'season.default.name',
    constraint uk_seasons_ordinal unique (ordinal)
);

--changeset jessebrands:add_season_requirements
create table if not exists season_requirements
(
    id          bigserial,
    season_id   bigint        not null,
    min_version varchar(64),
    max_version varchar(64),
    branches    varchar(32)[] not null default '{}',
    constraint pk_season_requirements primary key (id, season_id),
    constraint fk_season_requirements_season foreign key (season_id) references seasons(id)
        on delete cascade
        on update cascade
);
