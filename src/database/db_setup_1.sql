-- Database user

CREATE USER 'iamadmin'@'localhost' IDENTIFIED BY 'iamadmin';

GRANT ALL PRIVILEGES ON * . * TO 'iamadmin'@'localhost';

-- Create Schema 

DROP SCHEMA IF EXISTS `Managing-Employee-Made-Simple-Schema`;

CREATE SCHEMA `Managing-Employee-Made-Simple-Schema`;

use `Managing-Employee-Made-Simple-Schema`;


-- Employee table

DROP TABLE IF EXISTS `employee`;

CREATE TABLE `employee` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `designation` varchar(50) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `address` varchar(250) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `leaveapp` bit DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

-- Relation table

DROP TABLE IF EXISTS `empmapper`;

CREATE TABLE `empmapper` (
	`id` int(10) NOT NULL AUTO_INCREMENT,
	`emp_id` int(10) NOT NULL,
    `mang_id` int(10) NOT NULL,
    CONSTRAINT `FK_emp_id` FOREIGN KEY (`emp_id`) REFERENCES `employee`(`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
    CONSTRAINT `FK_mang_id` FOREIGN KEY (`mang_id`) REFERENCES `employee`(`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
    PRIMARY KEY (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
