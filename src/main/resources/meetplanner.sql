/*

SQLyog Ultimate v8.55 
MySQL - 5.6.14 : Database - meetplanner

*********************************************************************

*/



/*!40101 SET NAMES utf8 */;



/*!40101 SET SQL_MODE=''*/;



/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;

/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

CREATE DATABASE /*!32312 IF NOT EXISTS*/`meetplanner` /*!40100 DEFAULT CHARACTER SET utf8 */;



USE `meetplanner`;



/*Table structure for table `age_groups` */



DROP TABLE IF EXISTS `age_groups`;



CREATE TABLE `age_groups` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `age_group` varchar(40) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;



/*Data for the table `age_groups` */



insert  into `age_groups`(`id`,`age_group`) values (1,'18-22'),(2,'22-30'),(3,'Over 30');



/*Table structure for table `athlete` */



DROP TABLE IF EXISTS `athlete`;



CREATE TABLE `athlete` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `date_of_birth` date DEFAULT NULL,
  `group_id` int(10) DEFAULT NULL,
  `nic` varchar(20) DEFAULT NULL,
  `employee_no` varchar(20) NOT NULL,
  `gender` char(1) NOT NULL,
  `bib` int(10) DEFAULT NULL,
  `age_group_id` int(10) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_bib` (`bib`),
  KEY `FK_athlete_age_group` (`age_group_id`),
  CONSTRAINT `FK_athlete_age_group` FOREIGN KEY (`age_group_id`) REFERENCES `age_groups` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='athlete related details';



/*Data for the table `athlete` */



/*Table structure for table `athlete_events` */



DROP TABLE IF EXISTS `athlete_events`;



CREATE TABLE `athlete_events` (
  `athlete_id` int(10) NOT NULL,
  `event_id` int(10) NOT NULL,
  `performance` double NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



/*Data for the table `athlete_events` */



/*Table structure for table `events` */



DROP TABLE IF EXISTS `events`;



CREATE TABLE `events` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `event_name` varchar(100) NOT NULL,
  `type` varchar(5) NOT NULL,
  `participants` varchar(5) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;



/*Data for the table `events` */



insert  into `events`(`id`,`event_name`,`type`,`participants`) values (1,'Championship WOMEN','T','F'),(2,'NOVICES  WOMEN','F','F'),(3,'MEN CHAMPIONSHIPS','T','M'),(4,'NOVICES MEN','T','M'),(5,'over 30 women','T','F'),(6,'OVER 35 WOMEN','F','F'),(7,'OVER 40 WOMEN','T','F'),(8,'OVER 45 WOMEN','T','M'),(9,'OVER 50 WOMEN','T','M'),(10,'OVER 35 MEN','F','M'),(11,'OVER 40 MEN','T','M'),(12,'test event','F','M');



/*Table structure for table `groups` */



DROP TABLE IF EXISTS `groups`;



CREATE TABLE `groups` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;



/*Data for the table `groups` */



insert  into `groups`(`id`,`name`) values (1,'Virtusa'),(2,'Hshenid');



/*Table structure for table `role` */



DROP TABLE IF EXISTS `role`;



CREATE TABLE `role` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;



/*Data for the table `role` */



insert  into `role`(`id`,`name`) values (1,'ROLE_USER'),(2,'ROLE_TELLER');



/*Table structure for table `roles` */



DROP TABLE IF EXISTS `roles`;



CREATE TABLE `roles` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;



/*Data for the table `roles` */



insert  into `roles`(`id`,`name`) values (1,'ROLE_USER'),(2,'ROLE_ADMIN');



/*Table structure for table `user_roles` */



DROP TABLE IF EXISTS `user_roles`;



CREATE TABLE `user_roles` (
  `user_id` int(10) DEFAULT NULL,
  `role_id` int(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



/*Data for the table `user_roles` */



insert  into `user_roles`(`user_id`,`role_id`) values (1,1),(2,2);



/*Table structure for table `users` */



DROP TABLE IF EXISTS `users`;



CREATE TABLE `users` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user-name` varchar(100) NOT NULL,
  `password` varchar(200) DEFAULT NULL,
  `enabled` binary(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;



/*Data for the table `users` */



insert  into `users`(`id`,`user-name`,`password`,`enabled`) values (1,'lakmal','098f6bcd4621d373cade4e832627b4f6','1'),(2,'kamal','098f6bcd4621d373cade4e832627b4f6','1');



/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;

/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;

/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;

/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

