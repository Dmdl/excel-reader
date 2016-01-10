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
  `from_date` date NOT NULL,
  `to_date` date NOT NULL,
  `bib_from` int(11) NOT NULL,
  `bib_to` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;




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
  `bib` varchar(10) DEFAULT NULL,
  `age_group_id` int(10) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uc_BiBnum` (`bib`),
  KEY `FK_athlete_age_group` (`age_group_id`),
  CONSTRAINT `FK_athlete_age_group` FOREIGN KEY (`age_group_id`) REFERENCES `age_groups` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1 COMMENT='athlete related details';




/*Table structure for table `athlete_events` */



DROP TABLE IF EXISTS `athlete_events`;



CREATE TABLE `athlete_events` (
  `athlete_id` int(10) NOT NULL,
  `event_id` int(10) NOT NULL,
  `performance` double NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;




/*Table structure for table `event_age_groups` */



DROP TABLE IF EXISTS `event_age_groups`;



CREATE TABLE `event_age_groups` (
  `event_id` int(10) DEFAULT NULL,
  `age_group_id` int(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



/*Table structure for table `event_category` */



DROP TABLE IF EXISTS `event_category`;



CREATE TABLE `event_category` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(100) NOT NULL,
  `point_first` double DEFAULT NULL,
  `point_second` double DEFAULT NULL,
  `point_third` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;



/*Table structure for table `events` */



DROP TABLE IF EXISTS `events`;



CREATE TABLE `events` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `event_name` varchar(100) NOT NULL,
  `type` varchar(5) NOT NULL,
  `participants` varchar(5) NOT NULL,
  `event_category_id` int(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;



/*Table structure for table `groups` */



DROP TABLE IF EXISTS `groups`;



CREATE TABLE `groups` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;



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



insert  into `user_roles`(`user_id`,`role_id`) values (1,1),(1,2),(2,1),(3,1);



/*Table structure for table `users` */



DROP TABLE IF EXISTS `users`;



CREATE TABLE `users` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(100) NOT NULL,
  `password` varchar(200) DEFAULT NULL,
  `enabled` binary(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;



/*Data for the table `users` */



insert  into `users`(`id`,`user_name`,`password`,`enabled`) values (1,'lakmal','$2a$10$2Y0nTcM7T006BGaGNYv84uNjkbdAum2FZksZAPJEt..Q62ICFpsUu','1'),(2,'kamal','$2a$10$e1PFHESECu/gbO3RPG3//.BKhecZtlU34cyQ2.UsivyAXjA33CYQy','1'),(3,'test','$2a$10$bUzvo1emYOgekLQJlxC8I.V0iRR8FiMS25ZW1nC3KHydnahXtSj.S','1');



/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;

/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;

/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;

/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

