DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS cashaccount;
DROP TABLE IF EXISTS creditaccount;
DROP TABLE IF EXISTS transfer;
DROP TABLE IF EXISTS transaction;

create table account (
    username varchar(80) not null,
    name varchar(80) not null,
    surname varchar(80) not null,
    password varchar(80) not null,
    constraint pk_account primary key (username)
);

create table cashaccount (
    id int,
    number varchar(80) not null,
    username varchar(80)  not null,
    availablebalance double precision,
    description varchar(80)  not null,
    constraint pk_cashaccount primary key (id)
);

create table creditaccount(
    id int,
    number varchar(80) not null,
    username varchar(80)  not null,
    description varchar(80)  not null,
    availablebalance double precision,
    cashaccountid int,
    constraint pk_creditaccount primary key (id)
);


create table transfer(
    id serial,
    fromAccount varchar(80) not null,
    toAccount varchar(80)  not null,
    description varchar(80)  not null,
    amount double precision,
    fee double precision,
    username varchar(80)  not null,
    date TIMESTAMP,
    type varchar(80) not null,
    status varchar(10) not null,
    options varchar(50) not null,
    email varchar(80),
    telephone varchar(80),
    constraint pk_transfer primary key (id)
);

create table transaction(
    id serial,
    date TIMESTAMP,
    description varchar(80)  not null,
    number varchar(80) not null,
    amount double precision,
    availablebalance double precision,
    constraint pk_transaction primary key (id)
);


create table alert_conditions(
	id serial,
	alertid INTEGER not null,
    username varchar(80)  not null,
    account varchar(80)  not null,
    type varchar(80) not null,
    subtype varchar(80) not null,
    value varchar(80) not null,
    constraint pk_alert_conditions primary key (id)
);


create table alert(
	id serial,
	name varchar(80)  not null,
	type varchar(80) not null,
    username varchar(80)  not null,
    email varchar(80)  not null,
    telephone varchar(80) not null,
    constraint pk_alert primary key (id)
);