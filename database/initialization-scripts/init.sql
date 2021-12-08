create table if not exists USERS.USER(
    ID INTEGER not null constraint USER_PK primary key,
    "Name" VARCHAR(50) not null,
    "Password" VARCHAR(50) not null,
    "Surname" VARCHAR(50) not null,
    "Email" VARCHAR(50) not null,
    "Type" VARCHAR(50) not null,
    "IsActive" BOOLEAN default TRUE not null
);
