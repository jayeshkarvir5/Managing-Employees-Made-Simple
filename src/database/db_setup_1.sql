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
  `experience` int(10) DEFAULT 1,
  `techstack` varchar(250) DEFAULT "java,spring",
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

-- leave table

DROP TABLE IF EXISTS `leave_application`;

CREATE TABLE `leave_application`(
	`id` int(10) NOT NULL AUTO_INCREMENT,
    `emp_id` int(10) NOT NULL,
    `days` int(10) NOT NULL,
    `approved` bit DEFAULT 0,
    CONSTRAINT `FK_emp_id_la` FOREIGN KEY (`emp_id`) REFERENCES `employee`(`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
    PRIMARY KEY (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

-- Projects table

DROP TABLE IF EXISTS `project`;

CREATE TABLE `project`(
	`id` int(10) NOT NULL AUTO_INCREMENT,
    `name` varchar(50) NOT NULL,
    `description` varchar(300) NOT NULL,
    `client_name` varchar(50)  NOT NULL,
    `duration` int(10) DEFAULT 10,
    PRIMARY KEY (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1; 

DROP TABLE IF EXISTS `employee_project`;
CREATE TABLE `employee_project`(
	`emp_id` int(10) NOT NULL,
    `project_id` int(10) NOT NULL,
    CONSTRAINT `FK_EMPLOYEE` FOREIGN KEY (`emp_id`) 
	REFERENCES `employee` (`id`) 
	ON DELETE NO ACTION ON UPDATE NO ACTION,
  
	CONSTRAINT `FK_PROJECT` FOREIGN KEY (`project_id`) 
	REFERENCES `project` (`id`) 
	ON DELETE NO ACTION ON UPDATE NO ACTION
)ENGINE=InnoDB DEFAULT CHARSET=latin1;