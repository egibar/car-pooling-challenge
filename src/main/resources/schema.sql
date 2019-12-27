create table if not exists car (
    id bigint primary key,
    seats tinyint not null
    );
create table if not exists journey (
    id bigint primary key,
    people tinyint not null,
    car_id bigint,
    FOREIGN KEY (car_id) REFERENCES car (id)
    );