--liquibase formatted sql

--changeset jessebrands:01_extensions
create extension if not exists "uuid-ossp";
