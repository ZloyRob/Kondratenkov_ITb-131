INSERT INTO USER(LOGIN, PASS , SALT ) VALUES  ('jdoe', '0b6ae56859971bf3a34056e08b293b02', 'M5(ITYK');
INSERT INTO USER(LOGIN, PASS , SALT ) VALUES  ('jrow', '22f87b1c1234efb455957ced38dba4c6', '?];AN!I');
INSERT INTO RESOURCE(PATH, USERID , ROLE ) VALUES ('a', 1, 'READ');
INSERT INTO RESOURCE(PATH, USERID , ROLE ) VALUES ('a.b', 1, 'WRITE');
INSERT INTO RESOURCE(PATH, USERID , ROLE ) VALUES ('a.b.c', 2, 'EXECUTE');
INSERT INTO RESOURCE(PATH, USERID , ROLE ) VALUES ('a.bc', 1, 'EXECUTE');