CREATE DATABASE aviatickets
    WITH
    OWNER = development;

create table public.airports
(
    id_airport   bigserial not null
        constraint airports_pk
            primary key
        unique,
    name_airport varchar(50)           not null,
    city         varchar(50)           not null,
    longitude    real                  not null,
    latitude     real                  not null,
    timezone     text                  not null,
    created      timestamp(6)          not null,
    changed      timestamp(6)          not null,
    is_deleted   boolean default false not null,
    country      varchar(30)           not null
);

alter table public.airports
    owner to development;

create index location_index
    on public.airports (country, city);

create index coordinates_index
    on public.airports (longitude, latitude);

create table public.c_plane_types
(
    id_plane_type serial
        constraint plane_type_pkey
            primary key,
    plane_type    varchar(20)           not null,
    created       timestamp(6)          not null,
    changed       timestamp(6)          not null,
    is_deleted    boolean default false not null
);

alter table public.c_plane_types
    owner to development;


create table public.c_flight_status
(
    id_flight_status   serial
        primary key,
    name_flight_status varchar(30)           not null,
    created            timestamp(6)          not null,
    changed            timestamp(6)          not null,
    is_deleted         boolean default false not null
);

alter table public.c_flight_status
    owner to development;

create table public.flights
(
    id_flight            bigserial
        primary key,
    flight_number        varchar(10)  not null
        unique,
    id_plane_type        bigint       not null
        constraint plane_type_fk
            references public.c_plane_types,
    id_departure_airport bigint       not null
        constraint departure_airport_fk
            references public.airports,
    id_arrival_airport   bigint       not null
        constraint arrival_airport_fk
            references public.airports,
    departure_time       timestamp(6) not null,
    arrival_time         timestamp(6) not null,
    id_flight_status     bigint       not null
        constraint flight_status_fk
            references public.c_flight_status,
    created              timestamp(6) not null,
    changed              timestamp(6) not null,
    is_deleted           boolean default false
);

alter table public.flights
    owner to development;

create index time_flight_index
    on public.flights (arrival_time, departure_time);

create table public.c_airlines
(
    id_airline   serial
        primary key,
    name_airline varchar(50)           not null,
    code_airline varchar(3)            not null,
    created      timestamp(6)          not null,
    changed      timestamp(6)          not null,
    is_deleted   boolean default false not null
);

alter table public.c_airlines
    owner to development;

create table public.c_ticket_status
(
    id_ticket_status   serial
        constraint ticket_status_pkey
            primary key,
    name_ticket_status varchar(20)           not null,
    created            timestamp(6)          not null,
    changed            timestamp(6)          not null,
    is_deleted         boolean default false not null
);

alter table public.c_ticket_status
    owner to development;


create table public.c_ticket_class
(
    id_ticket_class serial
        constraint class_ticket_pkey
            primary key,
    name_class      varchar(30)           not null,
    created         timestamp(6)          not null,
    changed         timestamp(6)          not null,
    is_deleted      boolean default false not null
);

alter table public.c_ticket_class
    owner to development;

create table public.passengers
(
    id_pass     bigserial
        primary key,
    full_name   varchar(50)           not null,
    personal_id varchar(50)           not null
        unique,
    miles       real,
    created     timestamp(6)          not null,
    changed     timestamp(6)          not null,
    is_deleted  boolean default false not null
);

alter table public.passengers
    owner to development;

create unique index full_name_index
    on public.passengers (full_name);

create table public.c_document_type
(
    id_document_type serial
        constraint document_type_pkey
            primary key,
    doc_type         varchar(30)           not null
        constraint document_type_document_type_key
            unique,
    created          timestamp(6)          not null,
    changed          timestamp(6)          not null,
    is_deleted       boolean default false not null
);

alter table public.c_document_type
    owner to development;

create table public.tickets
(
    id_ticket        bigserial
        primary key,
    id_pass          bigint
        constraint id_pass_fk
            references public.passengers,
    id_ticket_status integer               not null
        constraint id_ticket_status_fk
            references public.c_ticket_status,
    price            numeric(10, 2)        not null,
    id_flight        bigint                not null
        constraint id_flight_fk
            references public.flights,
    number_place     varchar(15),
    created          timestamp(6)          not null,
    changed          timestamp(6)          not null,
    is_deleted       boolean default false not null,
    id_ticket_class  integer               not null
        constraint id_ticket_class_fk
            references public.c_ticket_class,
    id_airline       integer               not null
        constraint airlines_fk
            references public.c_airlines
);

alter table public.tickets
    owner to development;

create index changed_index
    on public.tickets (changed desc);

create index created_index
    on public.tickets (created desc);

create table public.c_roles
(
    id_role    serial
        constraint roles_pkey
            primary key,
    role_name  varchar(100)          not null,
    created    timestamp(6)          not null,
    changed    timestamp(6)          not null,
    is_deleted boolean default false not null
);

alter table public.c_roles
    owner to development;

create table public.users
(
    id_user       bigserial
        constraint user_id_user_key
            primary key,
    email         varchar(30)           not null
        constraint user_email_key
            unique,
    user_password varchar(200)          not null,
    created       timestamp(6)          not null,
    changed       timestamp(6)          not null,
    is_deleted    boolean default false not null,
    id_pass       bigint
        constraint user_id_pass_key
            unique,
    id_role       integer               not null
        constraint users_roles
            references public.c_roles
);

alter table public.users
    owner to development;

create table public.document_pass
(
    id_document_pass bigserial
        primary key,
    id_document_type bigint                not null
        constraint id_document_type_fk
            references public.c_document_type,
    document_num     varchar(30)           not null,
    id_pass          bigint                not null
        constraint id_pass_fk
            references public.passengers,
    created          timestamp(6)          not null,
    changed          timestamp(6)          not null,
    is_deleted       boolean default false not null
);

alter table public.document_pass
    owner to development;

create index changed_doc_index
    on public.document_pass (changed desc);

