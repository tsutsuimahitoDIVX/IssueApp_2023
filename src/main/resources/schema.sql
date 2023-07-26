CREATE TABLE if NOT EXISTS issues (
    id         INT       NOT NULL AUTO_INCREMENT,
    title      VARCHAR(256) NOT NULL,
    content    VARCHAR(256) NOT NULL,
    period     VARCHAR(256) NOT NULL,
    importance VARCHAR(256) NOT NULL,
    user_id    Integer      NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user(id)
 );

 CREATE TABLE IF NOT EXISTS user (
    id       INT             NOT NULL AUTO_INCREMENT,
    username VARCHAR(128)    NOT NULL UNIQUE,
    password VARCHAR(512)    NOT NULL,
    PRIMARY KEY (id)
 );

 CREATE TABLE IF NOT EXISTS comment (
   id       INT          NOT NULL AUTO_INCREMENT,
   message  VARCHAR(256) NOT NULL,
   user_id  INTEGER      NOT NULL,
   issue_id INTEGER      NOT NULL,
   PRIMARY KEY (id),
   FOREIGN KEY (user_id)  REFERENCES user(id),
   FOREIGN KEY (issue_id) REFERENCES issues(id)
  );

