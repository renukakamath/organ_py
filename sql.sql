/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 5.7.9 : Database - organ_kmm
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`organ_kmm` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `organ_kmm`;

/*Table structure for table `chat` */

DROP TABLE IF EXISTS `chat`;

CREATE TABLE `chat` (
  `chat_id` int(11) NOT NULL AUTO_INCREMENT,
  `sender_id` int(11) DEFAULT NULL,
  `receiver_id` int(11) DEFAULT NULL,
  `message` varchar(100) DEFAULT NULL,
  `dtae` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`chat_id`)
) ENGINE=MyISAM AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;

/*Data for the table `chat` */

insert  into `chat`(`chat_id`,`sender_id`,`receiver_id`,`message`,`dtae`) values 
(3,4,2,'hello','5/3/22'),
(2,2,4,'hi','2022-02-22 22:21:08'),
(4,2,4,'hi','2022-02-22 22:22:39'),
(5,5,6,'hiiii','2022-03-10'),
(6,6,5,'hloo','2022-03-10'),
(7,5,6,'hloo','2022-03-11'),
(8,5,4,'hloo','2022-03-11'),
(9,2,5,'hiiii','2022-03-11 09:37:24'),
(10,2,5,'hlo','2022-03-11 09:38:01'),
(11,5,2,'hloo','2022-03-11'),
(12,5,2,'hii','2022-03-11'),
(13,7,4,'hai','2023-02-06 15:48:58'),
(14,9,7,'hai','2023-02-06'),
(15,7,9,'helo','2023-02-06 16:28:07'),
(16,7,9,'how are you','2023-02-06 16:32:45'),
(17,7,9,'','2023-02-06 16:33:41'),
(18,7,9,'','2023-02-06 16:33:56'),
(19,9,6,'hai','2023-02-06'),
(20,6,9,'helo','2023-02-06'),
(21,6,9,'hhh','2023-02-06');

/*Table structure for table `complaint` */

DROP TABLE IF EXISTS `complaint`;

CREATE TABLE `complaint` (
  `complaint_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `complaint` varchar(100) DEFAULT NULL,
  `reply` varchar(100) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`complaint_id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `complaint` */

insert  into `complaint`(`complaint_id`,`user_id`,`complaint`,`reply`,`date`) values 
(1,1,'ffyh','ok','12/4/22'),
(2,3,'hhhh','helo','2022-03-10'),
(3,3,'hhhh','pending','2022-03-10'),
(4,2,'rrrrr','pending','2022-03-10'),
(5,4,'hello','pending','2023-02-06');

/*Table structure for table `donation` */

DROP TABLE IF EXISTS `donation`;

CREATE TABLE `donation` (
  `donation_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `organ_name` varchar(100) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`donation_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `donation` */

insert  into `donation`(`donation_id`,`user_id`,`title`,`organ_name`,`date`,`status`) values 
(1,1,'fafd','sfda','asdf','Accepted'),
(2,2,'abc','abc','2022-03-10','Accepted'),
(3,3,'ccc','cccc','2022-03-10','pending'),
(4,4,'heart donation','heart','2023-02-06','Accepted');

/*Table structure for table `hospital` */

DROP TABLE IF EXISTS `hospital`;

CREATE TABLE `hospital` (
  `hospital_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `hospital_name` varchar(100) DEFAULT NULL,
  `place` varchar(100) DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`hospital_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `hospital` */

insert  into `hospital`(`hospital_id`,`login_id`,`hospital_name`,`place`,`phone`,`email`) values 
(1,2,'kmk','kochi','1234567890','qq@gmail.com'),
(2,7,'lisie hospital','kaloor','98765412302','lisie@gmail.com');

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `login_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `usertype` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`login_id`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

/*Data for the table `login` */

insert  into `login`(`login_id`,`username`,`password`,`usertype`) values 
(1,'admin','admin','admin'),
(2,'kmk','kmk','hospitalss'),
(4,'anu','anu','user'),
(5,'mariya','mariya','user'),
(6,'tiss','tiss','user'),
(7,'lisi','lisi','hospital'),
(9,'user1','user1','user'),
(8,NULL,NULL,NULL),
(10,'ann','ann','ngo');

/*Table structure for table `post` */

DROP TABLE IF EXISTS `post`;

CREATE TABLE `post` (
  `post_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `organ_name` varchar(100) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `operation_date` varchar(100) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`post_id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `post` */

insert  into `post`(`post_id`,`user_id`,`title`,`organ_name`,`description`,`operation_date`,`date`,`status`,`type`) values 
(1,1,'ds','dfs','fsdf','2022-03-25','10/2/2020','rejected','user'),
(2,2,'Aaaa','aaaa','aaaaaa','2022-03-23','2022-03-10','accepted','user'),
(3,3,'bbb','bbbbb','bbbbbbb','2023-02-08','2022-03-10','pending','user'),
(4,4,'i neew organ','eye','please donate eye','pending','2022-03-10','pending','user'),
(5,2,'request','heart','need heart','2023-02-09','2023-02-07','pending','hospital');

/*Table structure for table `request` */

DROP TABLE IF EXISTS `request`;

CREATE TABLE `request` (
  `request_id` int(11) NOT NULL AUTO_INCREMENT,
  `sender_id` int(11) DEFAULT NULL,
  `hospital_id` int(11) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`request_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

/*Data for the table `request` */

insert  into `request`(`request_id`,`sender_id`,`hospital_id`,`status`,`type`) values 
(1,1,1,'Accepted','donation'),
(2,1,1,'accepted','post'),
(3,2,1,'Accepted','donation'),
(4,2,1,'accepted','post'),
(5,3,2,'accepted','post'),
(6,4,2,'accepted','post'),
(7,4,2,'Accepted','donation'),
(8,5,2,'accepted','post');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `first_name` varchar(100) DEFAULT NULL,
  `last_name` varchar(100) DEFAULT NULL,
  `place` varchar(100) DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `user` */

insert  into `user`(`user_id`,`login_id`,`first_name`,`last_name`,`place`,`phone`,`email`) values 
(1,4,'sdfsd','sdfsf','sfs','sfsf','sdfs'),
(2,5,'Mariya','Thomas','Alappuzha','8096745123','sgjkk@gmail.com'),
(3,6,'Tiss','mary','ekm','80741236980','sfg@gmail.com'),
(4,9,'anzari','an','kollam','9874563210','anzari@gmail.com');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
