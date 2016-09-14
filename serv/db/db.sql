-- represent a person
CREATE TABLE Person(
       id INT PRIMARY KEY,
       name VARCHAR(32),
       lastscan DATETIME,
       startscan DATETIME,
       );

-- a link on a search engine
CREATE TABLE RefLinks(
       pers INT,
       link VARCHAR(256),
       PRIMARY KEY(pers, link),
       FOREIGN KEY(pers) REFERENCES Person(id)
       );

-- facebook account related to this person
CREATE TABLE FbAccount(
       id INT PRIMARY KEY,
       fbid VARCHAR(64) UNIQUE,
       persref INT,
       FOREIGN KEY (persref) REFERENCES Person(id)
       );
