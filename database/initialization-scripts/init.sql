create table if not exists Users.User
(
    Id INTEGER not null primary key,
    Type VARCHAR(255) not null,
    Name VARCHAR(255) not null,
    Surname VARCHAR(255) not null,
    Email VARCHAR(255) not null,
    Password VARCHAR(255) not null,
    IsActive VARCHAR(1) not null
);