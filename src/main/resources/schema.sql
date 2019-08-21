DROP TABLE IF EXISTS tasks;
 
CREATE TABLE tasks (
	id BIGINT AUTO_INCREMENT  PRIMARY KEY,
	name VARCHAR(100) NOT NULL,
	description VARCHAR(250) DEFAULT NULL,
	deadline DATE NOT NULL,
	is_completed BOOLEAN DEFAULT FALSE,
	is_deleted BOOLEAN DEFAULT FALSE
);