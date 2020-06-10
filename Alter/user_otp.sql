/*
SQLyog Community v11.42 (64 bit)
MySQL - 5.6.21 : Database - vpark
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`vpark` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `vpark`;

/*Table structure for table `user_otp` */

DROP TABLE IF EXISTS `user_otp`;

CREATE TABLE `user_otp` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `mobile_no` varchar(11) NOT NULL,
  `otp` varchar(5) NOT NULL,
  `validate_time` datetime NOT NULL,
  `status` varchar(20) NOT NULL,
  `details` varchar(50) NOT NULL,
  `modified_on` datetime NOT NULL,
  `linux_modified_on` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
