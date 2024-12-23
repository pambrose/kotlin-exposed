create schema IF NOT EXISTS schema1;

set search_path to schema1;

create table names
(
    primary_id SERIAL PRIMARY KEY,
    created    TIMESTAMPTZ DEFAULT NOW(),
    name       TEXT NOT NULL
);
