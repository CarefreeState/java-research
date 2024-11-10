
drop database if exists `rltest`;
create database `rltest` character set utf8mb4 collate utf8mb4_bin;
use `rltest`;


drop table if exists user;
create table user(
    userId int primary key auto_increment,
    username varchar(50) UNIQUE,
    password varchar(100)
);
