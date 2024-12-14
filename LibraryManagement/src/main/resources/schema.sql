create table if not exists Book(
    id identity primary key,
    title varchar(50) not null,
    author varchar(50) not null,
    isbn varchar(13) not null,
    quantity int not null
);