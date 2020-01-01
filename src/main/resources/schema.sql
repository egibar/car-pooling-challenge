create table if not exists car (
    id bigint primary key,
    seats smallint not null
    );
create table if not exists journey (
    id bigint primary key,
    people smallint not null,
    car_id bigint,
    create_date timestamp,
    FOREIGN KEY (car_id) REFERENCES car (id)
    ON DELETE CASCADE
    );
