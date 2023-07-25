create table if not exists issues (
    id bigint not null auto_increment,
    title varchar(256) not null,
    content varchar(256) not null,
    period varchar(256) not null,
    importance varchar(256) not null,
    user_id Integer not null,
    primary key (id),
    foreign key (user_id) references user(id)
 );

 CREATE TABLE IF NOT EXISTS user (
    id       INT             NOT NULL AUTO_INCREMENT,
    username VARCHAR(128)    NOT NULL,
    password VARCHAR(512)    NOT NULL,
    PRIMARY KEY (id)
 );

