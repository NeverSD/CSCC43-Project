use mydb;

Drop TABLE IF EXISTS Activity;
Drop TABLE IF EXISTS Shelters;
Drop TABLE IF EXISTS Users;
Drop TABLE IF EXISTS Comments;

create table Users (
	user_account_number integer PRIMARY KEY,
    user_password varchar(50) NOT NULL,
    user_name varchar(50) NOT NULL,
    SIN integer NOT NULL,
    user_type integer NOT NULL,
    user_address varchar(100),
    date_of_birth date NOT NULL,
    occupation varchar(50),
    payment_info varchar(100) NOT NULL
);

create table Shelters (
	s_name varchar(50),
    s_host integer references Users(user_account_number),
    property_type integer ,
    longitude real NOT NULL,
	latitude real NOT NULL,
    s_address varchar(100) NOT NULL,
    characteristic varchar(100) NOT NULL,
    price real NOT NULL,
    city varchar(50),
    country varchar(50),
    zip_code varchar(50),
    primary key (s_name, s_host)
);

create table Activity (
	a_date date ,
    a_host integer references Shelters(s_host),
    a_shelter varchar(50) references Shelter(s_name),
    a_renter integer default NULL,
    a_status integer default 0,
    primary key (a_date, a_host,a_shelter)
);

create table Comments (
	content varchar(200),
    rate integer,
    commented integer,
    poster integer,
    PRIMARY KEY (commented, poster)
);

SET SQL_SAFE_UPDATES = 0;
 