create table if not exists hardware (
    id long identity,
    name varchar(64) not null,
    article_Code varchar(5) not null unique,
    article_Type varchar (8) not null,
    stock number not null,
    price double not null,
    primary key(id)
);

create table if not exists review(
    id identity,
    naslov varchar(64) not null,
    tekst varchar(256) not null,
    ocjena int(1) not null,
    hardware_id int not null,
    foreign key (hardware_id) references hardware(id) ON DELETE CASCADE,
    primary key(id)
);

create table if not exists authority(
    id identity not null,
    authority_name varchar (64) not null,
    primary key(id)
    );


create table if not exists user(
    id identity not null,
    username varchar (64) not null,
    password varchar (64) not null,
    primary key(id)
);

create table if not exists user_authority(
    user_id int not null,
    authority_id int not null,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES user(id),
    CONSTRAINT fk_authority FOREIGN KEY (authority_id) REFERENCES authority(id)
)

