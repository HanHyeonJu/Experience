CREATE DATABASE IF NOT EXISTS experiences
DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

use experiences;

DROP TABLE IF EXISTS todos;
CREATE TABLE todos( 
    id INT NOT NULL auto_increment, 
    title VARCHAR(100) default '' NOT NULL,
    stage VARCHAR(40) default '' NOT NULL,
    start DATETIME,
	target DATETIME,
    description VARCHAR(200),
    writer VARCHAR(45) default '' NOT NULL,
    primary key(id)
);

DROP TABLE IF EXISTS diarys;
CREATE TABLE diarys( 
	id INT NOT NULL auto_increment, 
    title VARCHAR(100) default '' NOT NULL,
    content TEXT NOT NULL,
    create_day TIMESTAMP NOT NULL default CURRENT_TIMESTAMP,
    update_day TIMESTAMP NOT NULL default CURRENT_TIMESTAMP,
    image VARCHAR(100) default '' NOT NULL,
	writer VARCHAR(45) default '' NOT NULL,
    primary key(id)
);

DROP TABLE IF EXISTS board;
CREATE TABLE board(
    bno INT NOT NULL auto_increment, 
    title VARCHAR(100) default '' NOT NULL,
    content TEXT NOT NULL,
    writer VARCHAR(45) default '' NOT NULL,
    regdate TIMESTAMP default CURRENT_TIMESTAMP NOT NULL ,
    updatedate TIMESTAMP default CURRENT_TIMESTAMP NOT NULL,
    view INT default '0' NOT NULL,
    primary key(bno)
);

DROP TABLE IF EXISTS reply;
CREATE TABLE reply(
    reply_no INT NOT NULL auto_increment, 
    bno INT NOT NULL,
    writer VARCHAR(45) default '' NOT NULL,
    content VARCHAR(200) default '' NOT NULL,
    create_at TIMESTAMP default CURRENT_TIMESTAMP NOT NULL ,
    update_at TIMESTAMP default CURRENT_TIMESTAMP NOT NULL,
    primary key(reply_no),
	foreign key(bno) references board(bno) on update cascade on delete cascade
);
