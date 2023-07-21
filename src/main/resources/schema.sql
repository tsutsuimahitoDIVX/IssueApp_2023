create table if not exists issues (
    id bigint not null primary key auto_increment,
    title varchar(256) not null,
    content varchar(256) not null,
    period varchar(256) not null,
    importance varchar(256) not null
 );

 CREATE TABLE IF NOT EXISTS user (
    id        INTEGER         NOT NULL AUTO_INCREMENT,
    username VARCHAR(128)    NOT NULL,
    password   VARCHAR(512)    NOT NULL,
    PRIMARY KEY (id)
 );

