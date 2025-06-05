DROP DATABASE IF EXISTS file_manager;
CREATE DATABASE IF NOT EXISTS file_manager;

USE file_manager;

CREATE TABLE user_account (
	id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(40) UNIQUE NOT NULL,
    keyword VARCHAR(100) NOT NULL,
    position ENUM("admin", "employee") NOT NULL,
    access BIT NOT NULL DEFAULT 1
);