DROP TABLE IF EXISTS USER;
DROP TABLE IF EXISTS RESOURCE;
DROP TABLE IF EXISTS ACCOUNTING;
CREATE TABLE USER (ID int IDENTITY(1,1) PRIMARY KEY, LOGIN VARCHAR(255), PASS VARCHAR(255), SALT VARCHAR(255));
CREATE TABLE RESOURCE (ID int IDENTITY(1,1) PRIMARY KEY, USERID INT, PATH VARCHAR(255), ROLE VARCHAR(255));
CREATE TABLE ACCOUNTING (RESID INT, DS DATE, DE DATE, VOL INT);
ALTER TABLE RESOURCE ADD  FOREIGN KEY (USERID) REFERENCES USER (ID);
ALTER TABLE ACCOUNTING ADD  FOREIGN KEY (RESID) REFERENCES RESOURCE (ID);
CREATE INDEX USER_ROLE_INDEX ON RESOURCE (USER_ID, ROLE);