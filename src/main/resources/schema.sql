DROP ALL OBJECTS;

create table if not exists USERS
(
    id       int          not null primary key auto_increment,
    email    varchar(255) not null,
    login    varchar(255) not null,
    name     varchar(255),
    birthday date,
    UNIQUE (email),
    UNIQUE (login)
);

create table if not exists FRIENDS
(
    user_id   int not null references USERS(id),
    friend_id int not null references USERS(id),
    PRIMARY KEY (user_id, friend_id)
);

create table if not exists MPA
(
    id   int not null primary key auto_increment,
    name varchar(255)
);

create table if not exists FILMS
(
    id           int          not null primary key auto_increment,
    name         varchar(255) not null,
    description  varchar(255) not null,
    release_date date,
    duration     int,
    mpa_id       int references MPA(id)
);

create table if not exists GENRES
(
    id   int not null primary key auto_increment,
    name varchar(255)
);

create table if not exists FILM_GENRES
(
    film_id  int not null references FILMS(id),
    genre_id int not null references GENRES(id),
    PRIMARY KEY (film_id, genre_id)
);

create table if not exists LIKES
(
    user_id int not null references USERS(id),
    film_id int not null references FILMS(id),
    PRIMARY KEY (user_id, film_id)
);