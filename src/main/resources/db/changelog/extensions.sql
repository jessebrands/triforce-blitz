--liquibase formatted sql

--changeset jessebrands:ext_uuid_ossp
create extension if not exists "uuid-ossp";
