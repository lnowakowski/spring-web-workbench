drop table items if exists;
create table items (id bigint not null primary key identity, number int not null unique, name varchar(50) not null unique, created timestamp not null, version int default 0);
