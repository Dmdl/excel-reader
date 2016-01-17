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
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;



/*Data for the table `age_groups` */



insert  into `age_groups`(`id`,`age_group`,`from_date`,`to_date`,`bib_from`,`bib_to`) values (20,'Under 13','2003-01-01','2004-12-31',100,1000),(21,'Under 15','2001-01-01','2002-12-31',1001,2000),(22,'Under 17','1999-01-01','2000-12-31',2001,3000);



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
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1 COMMENT='athlete related details';



/*Data for the table `athlete` */



insert  into `athlete`(`id`,`name`,`date_of_birth`,`group_id`,`nic`,`employee_no`,`gender`,`bib`,`age_group_id`) values (1,'H N P Silva','2003-01-02',9,'','','M','100',20),(2,'E D S R Edirisinghe','2003-01-23',9,'','','M','101',20),(3,'P L Chandika','2003-03-04',9,'','','M','102',20),(4,'Y G M Silva','2003-01-15',9,'1111','','M','103',20),(5,'M E Kodagoda','2004-04-06',9,'','','M','104',20),(6,'S W R R Pulle','2003-01-27',10,'','','M','105',20),(7,'A A U B Athapattu','2003-01-08',10,'','','M','106',20),(8,'G K C R Jayasekara','2004-01-19',10,'','','M','107',20),(9,'S P Thilakarathne','2003-10-10',12,'3224','','M','108',20),(10,'D M M Dissanayake','2004-01-11',12,'','','M','110',20),(11,'D D I Ananda','2002-01-02',12,'','','M','1003',21),(12,'M A K Perera','2001-01-23',12,'','','M','1004',21),(13,'G R Amarasinghe','2002-03-04',12,'','','M','1005',21),(14,'S A D N Pradeep','2002-01-15',12,'','','M','1006',21),(15,'S D K Gunasekara','2002-04-06',11,'','','M','1001',21),(16,'Manjula Kumara','2001-01-27',11,'','','M','1002',21),(17,'M D R P Munsinghe','2001-10-01',13,'','','F','1507',21),(18,'P G C Prasad','2002-10-10',13,'','','F','1508',21),(19,'W D Dhammika','2001-11-01',13,'','','F','1509',21),(20,'R A D S K Ranasinghe','2002-12-11',13,'','','F','1510',21);



/*Table structure for table `athlete_events` */



DROP TABLE IF EXISTS `athlete_events`;



CREATE TABLE `athlete_events` (
  `athlete_id` int(10) NOT NULL,
  `event_id` int(10) NOT NULL,
  `performance` double NOT NULL DEFAULT '0',
  `place` int(2) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



/*Data for the table `athlete_events` */



insert  into `athlete_events`(`athlete_id`,`event_id`,`performance`,`place`) values (1,17,8.9,1),(1,25,0,0),(1,21,0,0),(2,17,9,2),(2,25,0,0),(3,17,9.2,3),(3,21,0,0),(3,22,0,0),(4,17,9.5,4),(4,21,0,0),(4,25,0,0),(5,17,10,5),(5,22,0,0),(6,17,0,0),(6,25,0,0),(7,17,0,0),(7,21,0,0),(7,25,0,0),(8,17,0,0),(8,22,0,0),(10,17,0,0),(10,22,0,0),(11,17,0,0),(11,20,0,0),(11,25,0,0),(12,22,0,0),(12,25,0,0),(13,20,0,0),(13,25,0,0),(14,17,0,0),(14,20,0,0),(14,25,0,0),(15,17,0,0),(15,25,0,0),(16,17,0,0),(16,22,0,0),(16,25,0,0),(17,18,0,0),(17,19,0,0),(17,26,0,0),(18,18,0,0),(18,26,0,0),(19,18,0,0),(19,23,0,0),(19,26,0,0),(20,18,0,0),(20,26,0,0),(9,17,0,0),(9,21,0,0);



/*Table structure for table `event_age_groups` */



DROP TABLE IF EXISTS `event_age_groups`;



CREATE TABLE `event_age_groups` (
  `event_id` int(10) DEFAULT NULL,
  `age_group_id` int(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



/*Data for the table `event_age_groups` */



insert  into `event_age_groups`(`event_id`,`age_group_id`) values (18,20),(18,21),(18,22),(19,20),(19,21),(19,22),(20,21),(20,22),(21,20),(23,20),(23,21),(23,22),(24,20),(24,21),(24,22),(25,20),(25,21),(25,22),(26,20),(26,21),(26,22),(17,20),(17,21),(17,22),(22,20),(22,21),(22,22),(27,21),(27,22),(27,20),(28,21),(28,22),(29,20),(29,21),(29,22);



/*Table structure for table `event_category` */



DROP TABLE IF EXISTS `event_category`;



CREATE TABLE `event_category` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(100) NOT NULL,
  `point_first` double NOT NULL DEFAULT '0',
  `point_second` double NOT NULL DEFAULT '0',
  `point_third` double NOT NULL DEFAULT '0',
  `point_forth` double NOT NULL DEFAULT '0',
  `point_fifth` double NOT NULL DEFAULT '0',
  `point_sixth` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;



/*Data for the table `event_category` */



insert  into `event_category`(`id`,`category_name`,`point_first`,`point_second`,`point_third`,`point_forth`,`point_fifth`,`point_sixth`) values (1,'Individual',15,10,5,1,1,0),(2,'Relay',7,5,2,0,0,0),(3,'test',10,5,3,0,0,0);



/*Table structure for table `events` */



DROP TABLE IF EXISTS `events`;



CREATE TABLE `events` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `event_name` varchar(100) NOT NULL,
  `type` varchar(5) NOT NULL,
  `participants` varchar(5) NOT NULL,
  `event_category_id` int(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;



/*Data for the table `events` */



insert  into `events`(`id`,`event_name`,`type`,`participants`,`event_category_id`) values (17,'100M','T','M',3),(18,'100M','T','F',3),(19,'100MH','T','F',3),(20,'110MH','T','M',3),(21,'100MH','T','M',3),(22,'Discuss Throw','F','M',3),(23,'Discuss Throw','F','F',3),(24,'4x100M','T','F',4),(25,'Long Jump','F','M',3),(26,'Long Jump','F','F',3),(27,'4x400M','T','M',4),(28,'4x400M','T','F',4),(29,'4x100M','T','M',4);



/*Table structure for table `groups` */



DROP TABLE IF EXISTS `groups`;



CREATE TABLE `groups` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;



/*Data for the table `groups` */



insert  into `groups`(`id`,`name`) values (9,'Virtusa'),(10,'Pearson'),(11,'NDB'),(12,'Airport & Aviation Services (SL) LTD'),(13,'Ceylon Biscuits LTD'),(14,'HNB- Updates');



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

