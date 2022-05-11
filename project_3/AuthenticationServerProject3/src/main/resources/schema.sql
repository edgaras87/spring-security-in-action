--DROP TABLE IF EXISTS `project3_auth_server`.`user`;
--DROP TABLE IF EXISTS `project3_auth_server`.`otp`;

CREATE TABLE IF NOT EXISTS `project3_auth_server`.`user` (
 `username` VARCHAR(45) NOT NULL,
 `password` TEXT NULL,
 PRIMARY KEY (`username`));
 
CREATE TABLE IF NOT EXISTS `project3_auth_server`.`otp` (
 `username` VARCHAR(45) NOT NULL,
 `code` VARCHAR(45) NULL,
 PRIMARY KEY (`username`));
