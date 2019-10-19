create table autors
(
    id bigint not null GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 )
        constraint autors_pkey
            primary key,
    first_name varchar(30) not null,
    last_name  varchar(30) not null
);

alter table autors
    owner to postgres;

create table audiences
(
    id   bigint not null GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 )
        constraint audience_pkey
            primary key,
    name varchar(100) not null
);

alter table audiences
    owner to postgres;

create table exhibits
(
    id bigint not null GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 )
        constraint exhibits_pkey
            primary key,
    type        varchar(30) not null,
    material    varchar(30),
    techic      varchar(30),
    audience_id bigint      not null
        constraint audience_id
            references audiences on delete cascade,
    name varchar(30) not null
);

alter table exhibits
    owner to postgres;

create table employees
(
    id bigint not null GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 )
        constraint employees_pkey primary key,
    first_name  varchar(30)  not null,
    last_name   varchar(30)  not null,
    position    varchar(30)  not null,
    login       varchar(30)  not null,
    password    varchar(100) not null,
    audience_id bigint
        constraint audience_id
            references audiences on delete cascade
);

alter table employees
    owner to postgres;

create table excursion
(
    id bigint not null GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 )
        constraint excursion_pkey
            primary key,
    name varchar(100) not null
);

alter table excursion
    owner to postgres;

create table timetable
(
    id bigint not null GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 )
        constraint timetable_pkey
            primary key,
    employee_id  bigint not null
        constraint employee_id
            references employees on delete cascade,
    excursion_id bigint not null
        constraint excursion_id
            references excursion on delete cascade,
    date_start   timestamp with time zone not null,
    date_end     timestamp with time zone not null
);

alter table timetable
    owner to postgres;

create table autor_exhibit
(
    autor_id bigint not null
        constraint autor_id
            references autors
            on delete cascade,
    exhibit_id bigint not null
        constraint exhibit_id
            references exhibits
            on delete cascade,
    constraint autor_exhibit_pkey
        primary key (autor_id, exhibit_id)
);

alter table autor_exhibit
    owner to postgres;

