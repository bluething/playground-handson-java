create table if not exists url
(
    id                    bigserial primary key,
    long_url              varchar(255),
    short_url             varchar(255),
    created_date          timestamp        not null default current_timestamp,
    updated_date          timestamp,
    );

create index url_long_url
    on url (long_url);
create index url_short_url
    on url (short_url);

CREATE SEQUENCE IF NOT EXISTS sequence_url START 11111;
