alter table if exists city add column created_by varchar(255);
alter table if exists city add column created_date timestamp(6);
alter table if exists city add column last_modified_by varchar(255);
alter table if exists city add column last_modified_date timestamp(6);

create table city_aud (
    id integer not null,
    rev integer not null,
    revtype tinyint,
    created_by varchar(255),
    created_date timestamp(6),
    last_modified_by varchar(255),
    last_modified_date timestamp(6),
    image_link varchar(1000),
    name varchar(255),
    primary key (rev, id)
);

create table revinfo (
    rev integer generated by default as identity,
    revtstmp bigint,
    primary key (rev)
);

alter table if exists city_aud add constraint ref_city_aud_rev_revinfo foreign key (rev) references revinfo;