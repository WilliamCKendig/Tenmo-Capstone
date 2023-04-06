BEGIN TRANSACTION;

DROP TABLE IF EXISTS tenmo_user, account, transfer_type, transfer;

DROP SEQUENCE IF EXISTS seq_user_id, seq_account_id, seq_transfer_id;

-- Sequence to start user_id values at 1001 instead of 1
CREATE SEQUENCE seq_user_id
  INCREMENT BY 1
  START WITH 1001
  NO MAXVALUE;

CREATE TABLE tenmo_user (
	user_id int NOT NULL DEFAULT nextval('seq_user_id'),
	username varchar(50) NOT NULL,
	password_hash varchar(200) NOT NULL,
	CONSTRAINT PK_tenmo_user PRIMARY KEY (user_id),
	CONSTRAINT UQ_username UNIQUE (username)
);

-- Sequence to start account_id values at 2001 instead of 1
-- Note: Use similar sequences with unique starting values for additional tables
CREATE SEQUENCE seq_account_id
  INCREMENT BY 1
  START WITH 2001
  NO MAXVALUE;

CREATE TABLE account (
	account_id int NOT NULL DEFAULT nextval('seq_account_id'),
	user_id int NOT NULL,
	balance decimal(13, 2) NOT NULL,
	CONSTRAINT PK_account PRIMARY KEY (account_id),
	CONSTRAINT FK_account_tenmo_user FOREIGN KEY (user_id) REFERENCES tenmo_user (user_id)
);

CREATE TABLE transfer_type(
	transfer_type_id int NOT NULL,
	transfer_type_desc varchar(10) NOT NULL,
	CONSTRAINT PK_transfer_type PRIMARY KEY (transfer_type_id)
);


CREATE SEQUENCE seq_transfer_id
 INCREMENT BY 1
 START WITH 3001
 NO MAXVALUE;

CREATE TABLE transfer(
	transfer_id int NOT NULL DEFAULT nextval ('seq_transfer_id'),
	account_from int NOT NULL,
	account_to int NOT NULL,
	amount decimal(13, 2) NOT NULL,
	transfer_type_id int NOT NULL,
	user_to varchar(20) NOT NULL,
	user_from varchar(20) NOT NULL,
	CONSTRAINT PK_transfer PRIMARY KEY(transfer_id),
	CONSTRAINT FK_transfer_account_from FOREIGN KEY (account_from) REFERENCES account (account_id),
	CONSTRAINT FK_transfer_account_to FOREIGN KEY (account_to) REFERENCES account (account_id),
	CONSTRAINT FK_transfer_transfer_type FOREIGN KEY (transfer_type_id) REFERENCES transfer_type(transfer_type_id)
);

INSERT INTO transfer_type (transfer_type_id, transfer_type_desc)
	VALUES (1, 'Send'),
	VALUES (2, 'Receive');


INSERT INTO tenmo_user (username, password_hash)
VALUES ('bob', '$2a$10$G/MIQ7pUYupiVi72DxqHquxl73zfd7ZLNBoB2G6zUb.W16imI2.W2'),
       ('user', '$2a$10$Ud8gSvRS4G1MijNgxXWzcexeXlVs4kWDOkjE7JFIkNLKEuE57JAEy');

<<<<<<< HEAD
=======
INSERT INTO account (account_id, user_id, balance)
VALUES (2001, 1001, 1000),
       (2002, 1002, 600);

--INSERT INTO transfer (transfer_id, account_to, account_from, amount, transfer_type_id, user_to, user_from)
--VALUES (3001, 2001, 2002, 300, 1, 'bob', 'user'),
--    (3002, 2002, 2001, 400, 1, 'user', 'bob');


>>>>>>> 617f62594e8b8becbdd90ba768cb0b43e5aeba3e
COMMIT;