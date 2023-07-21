create table if not exists issues (
    id bigint not null primary key auto_increment,
    title varchar(256) not null,
    content varchar(256) not null,
    period varchar(256) not null,
    importance varchar(256) not null
 );

