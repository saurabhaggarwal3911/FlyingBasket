-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: flyingbasket
-- ------------------------------------------------------
-- Server version	5.5.5-10.1.22-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `appconfiguration`
--

DROP TABLE IF EXISTS `appconfiguration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `appconfiguration` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `value` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appconfiguration`
--

LOCK TABLES `appconfiguration` WRITE;
/*!40000 ALTER TABLE `appconfiguration` DISABLE KEYS */;
INSERT INTO `appconfiguration` VALUES (1,'HT_VERSION','1.0'),(2,'HT_FILE_VERSION','1.0'),(3,'APP_NAME','Flying Basket'),(4,'APP_URL','test'),(5,'ALLOW_ALERT_NOTIFICATION','false');
/*!40000 ALTER TABLE `appconfiguration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ROLE_ROOT_ADMIN'),(2,'ROLE_ORGANIZATION_ADMIN');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createdBy` int(11) DEFAULT NULL,
  `createdOn` datetime DEFAULT NULL,
  `modifiedBy` int(11) DEFAULT NULL,
  `modifiedOn` datetime DEFAULT NULL,
  `is_delete` bit(1) NOT NULL DEFAULT b'0',
  `email_id` varchar(255) NOT NULL,
  `login_info` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `user_id` varchar(50) NOT NULL,
  `isValid` bit(1) DEFAULT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK4gvcgfgnmp2yyayijhbvfs873` (`user_id`),
  KEY `FKi4ncga1wcki5b6ph9x069oynd` (`role_id`),
  CONSTRAINT `FKi4ncga1wcki5b6ph9x069oynd` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES 
(1,NULL,NULL,1,'2017-04-21 10:43:45','\0','Saurabh.9.Aggarwal1@flyingbasket.com',NULL,'Saurabh','$2a$10$NfCqelI8wGgGLmopBWPEn.zmr6HPTrohz62oPhQxSGbzsp4dez1zm','Saurabh.9.Aggarwal1@flyingbasket.com','',1),
(2,NULL,NULL,NULL,NULL,'\0','Saggarwal@q3tech.com',NULL,'Saurabh','admin','Saggarwal@q3tech.com','',2),
(3,1,'2017-04-21 14:28:50',NULL,NULL,'\0','Saurabh.9.Aggarwal@flyingbasket.com',NULL,'Saurabh','$2a$10$NfCqelI8wGgGLmopBWPEn.zmr6HPTrohz62oPhQxSGbzsp4dez1zm','Saurabh.9.Aggarwal@flyingbasket.com','',1),(4,1,'2017-04-21 14:43:53',1,'2017-04-21 14:44:29','\0','sahil.kaushik@flyingbasket.com',NULL,'Sahil Kaushik','$2a$10$NfCqelI8wGgGLmopBWPEn.zmr6HPTrohz62oPhQxSGbzsp4dez1zm','sahil.kaushik@flyingbasket.com','',2),(5,3,'2017-04-21 14:46:06',NULL,NULL,'\0','sheelendra.singh@flyingbasket.com',NULL,'Sheelu','$2a$10$uQmKQcmCZF6luMxAgloTge1C3.75lAkvYmf1.G018UE22D0waSc6G','sheelendra.singh@flyingbasket.com','',2),(6,1,'2017-04-21 14:47:35',NULL,NULL,'\0','kambhatt90@gmail.com',NULL,'kamlesh bhatt','$2a$10$u84Fwmo4yzOC74h1LlmiBuX2Vu0/sKXzsmZd8VXyhy1f5qiX9I.aW','kam_account1','',2),(7,6,'2017-04-21 14:55:04',NULL,NULL,'\0','harmanjot.kaur@flyingbasket.com',NULL,'Harman','$2a$10$PxXUtpsYf6qgWHZTw30TWepA4eiO1bg9WWShw/TeeHylXFwIfTbFy','harman_account1','',2),(8,3,'2017-04-24 10:12:07',NULL,NULL,'\0','Anil.chaudhary@flyingbasket.com',NULL,'Anil Kumar','$2a$10$ezPJeP..o7MosVXfZ4TppeKN14pUwjJAt.ZlcFD45UoCt7E3OtlAa','Anil.chaudhary@flyingbasket.com','',2);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-04-24 15:58:44



1	ROLE_SITE_ADMIN
2	ROLE_CLIENT_ADMIN
3	ROLE_WORKSPACE_ADMIN
4	ROLE_DELIVERY_ADMIN
5	USER
INSERT INTO `flyingbasket`.`appconfiguration` (`id`, `name`, `value`) VALUES ('1', 'HT_VERSION', '1.0');
INSERT INTO `flyingbasket`.`appconfiguration` (`id`, `name`, `value`) VALUES ('2', 'HT_FILE_VERSION', '1.0');
INSERT INTO `flyingbasket`.`appconfiguration` (`id`, `name`, `value`) VALUES ('3', 'APP_NAME', 'Flying Basket');
INSERT INTO `flyingbasket`.`appconfiguration` (`id`, `name`, `value`) VALUES ('4', 'APP_URL', 'test');
INSERT INTO `flyingbasket`.`appconfiguration` (`id`, `name`, `value`) VALUES ('5', 'ALLOW_ALERT_NOTIFICATION', 'false');


INSERT INTO `flyingbasket`.`Role` (`id`, `name`) VALUES ('1', 'ROLE_SITE_ADMIN');
INSERT INTO `flyingbasket`.`Role` (`id`, `name`) VALUES ('2', 'ROLE_CLIENT_ADMIN');
INSERT INTO `flyingbasket`.`Role` (`id`, `name`) VALUES ('3', 'ROLE_WORKSPACE_ADMIN');
INSERT INTO `flyingbasket`.`Role` (`id`, `name`) VALUES ('4', 'ROLE_DELIVERY_ADMIN');
INSERT INTO `flyingbasket`.`Role` (`id`, `name`) VALUES ('5', 'ROLE_USER');









INSERT INTO `flyingbasket`.`Brand` (`id`, `is_active`, `additional_info`, `description`, `name`) VALUES ('1', '1', 'test', 'test desc', 'Tata');
INSERT INTO `flyingbasket`.`Brand` (`id`, `is_active`, `additional_info`, `description`, `name`) VALUES ('2', '1', 'test info', 'test desc', 'name');


INSERT INTO `flyingbasket`.`Client` (`id`, `active`, `name`) VALUES ('1', '1', 'Flying Basket');

INSERT INTO `flyingbasket`.`Workspace` (`id`, `active`, `name`, `client_id`) VALUES ('1', '1', 'Flying Basket Workspace', '1');

INSERT INTO `flyingbasket`.`Users` (`id`, `address`, `is_delete`, `login_info`, `membership_type`, `mobile`, `name`, `password`, `reference_code`, `email_id`, `isValid`, `client_id`, `role_id`, `workspace_id`) VALUES ('1', 'GGN', '0', '0', 'GOLD', '9540126456', 'Saurabh Aggarwal', '$2a$10$NfCqelI8wGgGLmopBWPEn.zmr6HPTrohz62oPhQxSGbzsp4dez1zm', '9540126456', 'saurabhaggarwal05@gmail.com', '1', '1', '1', '1');

INSERT INTO `flyingbasket`.`Workspace__User` (`user_id`, `workspace_id`) VALUES ('1', '1');



INSERT INTO `flyingbasket`.`Category` (`id`, `is_active`, `additional_info`, `description`, `large_image_path`, `name`, `small_image_path`, `brand_id`) VALUES ('1', '1', 'test info', 'test desc', 'lasrge_image_url1', 'Category 1', 'small_image_url', '1');
INSERT INTO `flyingbasket`.`Category` (`id`, `is_active`, `additional_info`, `description`, `large_image_path`, `name`, `small_image_path`, `brand_id`) VALUES ('2', '1', 'test info', 'test desc2', 'lasrge_image_url2', 'Category 2', 'small_image_url', '1');
INSERT INTO `flyingbasket`.`Category` (`id`, `is_active`, `additional_info`, `description`, `large_image_path`, `name`, `small_image_path`, `brand_id`) VALUES ('3', '1', 'test info', 'test desc3', 'lasrge_image_url3', 'Category 3', 'small_image_url', '2');

INSERT INTO `flyingbasket`.`SubCategory` (`id`, `is_active`, `additional_info`, `description`, `large_image_path`, `name`, `small_image_path`, `brand_id`, `category_id`)VALUES('1', '1', 'test info', 'test desc', 'large_image_name 1', 'sub category 1', 'small_image_name 1', '1', '1');
INSERT INTO `flyingbasket`.`SubCategory` (`id`, `is_active`, `additional_info`, `description`, `large_image_path`, `name`, `small_image_path`, `brand_id`, `category_id`) VALUES ('2', '1', 'test info', 'test desc', 'large_image_name', 'sub category 2', 'small_image_name', '1', '2');

INSERT INTO `flyingbasket`.`SubCategory` (`id`, `is_active`, `additional_info`, `description`, `large_image_path`, `name`, `small_image_path`, `brand_id`, `category_id`) VALUES ('3', '1', 'test info', 'test desc', 'large_image_name', 'sub category 3', 'small_image_name', '1', '2');
INSERT INTO `flyingbasket`.`SubCategory` (`id`, `is_active`, `additional_info`, `description`, `large_image_path`, `name`, `small_image_path`, `brand_id`, `category_id`) VALUES ('4', '1', 'test info', 'test desc', 'large_image_name', 'sub category 4', 'small_image_name', '2', '3');

INSERT INTO `flyingbasket`.`Product_Type` (`id`, `name`) VALUES ('1', 'type1');

INSERT INTO `flyingbasket`.`Product_Type` (`id`, `name`) VALUES ('2', 'type1');
INSERT INTO `flyingbasket`.`Product_Type` (`id`, `name`) VALUES ('3', 'type1');

SELECT * FROM flyingbasket.products;

INSERT INTO `flyingbasket`.` 	` 
(`id`, `workspace_id`, `is_active`, `buy_cost`, `code`, `description`, `large_image_path`, `offer`, `old_price`, `price`, `small_image_path`, `stock`, `title`,   `brand_id`, `category_id`,`SubCategory_id`,`product_type_id`)
VALUES ('1', '1', '1', '405.0', 'xyxtscfhs693re', 'test desc', 'large_image_url', '1% off', '455.0', '450.0', 'small_image_url', '25', 'Tata tea 1kg',  '1', '1', '1','1');

INSERT INTO `flyingbasket`.`Products` 
(`id`, `workspace_id`, `is_active`, `buy_cost`, `code`, `description`, `large_image_path`, `offer`, `old_price`, `price`, `small_image_path`, `stock`, `title`,  `product_id`, `brand_id`, `category_id`,`SubCategory_id`,`product_type_id`)
 VALUES ('2', '1', '1', '45.0', 'xyxtscfhs693', 'test desc', 'large_image_url', '10% off', '55.0', '50.0', 'small_image_url', '25', 'Tata tea 100gm', '1', '1', '1', '1','1');

INSERT INTO `flyingbasket`.`Products` 
(`id`, `workspace_id`, `is_active`, `buy_cost`, `code`, `description`, `large_image_path`, `offer`, `old_price`, `price`, `small_image_path`, `stock`, `title`,  `product_id`, `brand_id`, `category_id`,`SubCategory_id`,`product_type_id`)
VALUES ('3', '1', '1', '80.0', 'xyxtscfhs69233', 'test desc', 'large_image_url', '', '', '60', 'small_image_url', '25', 'Tata tea 250gm', '1', '1', '2', '2','2');

INSERT INTO `flyingbasket`.`Products` 
(`id`, `workspace_id`, `is_active`, `buy_cost`, `code`, `description`, `large_image_path`, `offer`, `old_price`, `price`, `small_image_path`, `stock`, `title`,  `product_id`, `brand_id`, `category_id`,`SubCategory_id`,`product_type_id`)
VALUES ('4', '1', '1', '205.0', 'xyxtscfhs69343', 'test desc', 'large_image_url', '20% off', '', '200.0', 'small_image_url', '25', 'Tata tea 750gm', '1', '1', '2', '2','2');

INSERT INTO `flyingbasket`.`Products` 
(`id`, `workspace_id`, `is_active`, `buy_cost`, `code`, `description`, `large_image_path`, `offer`, `old_price`, `price`, `small_image_path`, `stock`, `title`,  `product_id`, `brand_id`, `category_id`,`SubCategory_id`,`product_type_id`)
VALUES ('5', '1', '1', '505.0', 'xyxtscfhs693d', 'test desc', 'large_image_url', '5% off', '595.0', '550.0', 'small_image_url', '25', 'Tata tea 1.5kg', '1', '1', '2', '3','2');

INSERT INTO `flyingbasket`.`Products` 
(`id`, `workspace_id`, `is_active`, `buy_cost`, `code`, `description`, `large_image_path`, `offer`, `old_price`, `price`, `small_image_path`, `stock`, `title`,  `product_id`, `brand_id`, `category_id`,`SubCategory_id`,`product_type_id`)
VALUES ('6', '1', '1', '705.0', 'xyxtscfhs693ds', 'test desc', 'large_image_url', '9% off', '755.0', '730.0', 'small_image_url', '25', 'Tata tea 2kg', '1', '1', '1', '1','1');


INSERT INTO `flyingbasket`.`Products` 
(`id`, `workspace_id`, `is_active`, `buy_cost`, `code`, `description`, `large_image_path`, `offer`, `old_price`, `price`, `small_image_path`, `stock`, `title`,  `product_id`, `brand_id`, `category_id`,`SubCategory_id`,`product_type_id`)
VALUES ('7', '1', '1', '705.0', 'xyxtscfhs693ds', 'test desc', 'large_image_url', '9% off', '755.0', '730.0', 'small_image_url', '25', 'test product', '1', '2', '3', '4','3');


INSERT INTO `flyingbasket`.`Product_Type__Subcateogry` (`category_id`, `product_id`, `SubCategory_id`, `product_type_id`) VALUES ('1', '1', '1', '1');
INSERT INTO `flyingbasket`.`Product_Type__Subcateogry` (`category_id`, `product_id`, `SubCategory_id`, `product_type_id`) VALUES ('1', '2', '1', '1');
INSERT INTO `flyingbasket`.`Product_Type__Subcateogry` (`category_id`, `product_id`, `SubCategory_id`, `product_type_id`) VALUES ('2', '3', '2', '2');
INSERT INTO `flyingbasket`.`Product_Type__Subcateogry` (`category_id`, `product_id`, `SubCategory_id`, `product_type_id`) VALUES ('2', '4', '2', '2');
INSERT INTO `flyingbasket`.`Product_Type__Subcateogry` (`category_id`, `product_id`, `SubCategory_id`, `product_type_id`) VALUES ('2', '5', '3', '2');
INSERT INTO `flyingbasket`.`Product_Type__Subcateogry` (`category_id`, `product_id`, `SubCategory_id`, `product_type_id`) VALUES ('1', '6', '1', '1');
INSERT INTO `flyingbasket`.`Product_Type__Subcateogry` (`category_id`, `product_id`, `SubCategory_id`, `product_type_id`) VALUES ('3', '7', '4', '3');





INSERT INTO `flyingbasket`.`appconfiguration` (`id`, `name`) VALUES ('7', 'SMTP_AUTHENTICATION');
INSERT INTO `flyingbasket`.`appconfiguration` (`id`, `name`) VALUES ('8', 'SMTP_HOST');
INSERT INTO `flyingbasket`.`appconfiguration` (`id`, `name`) VALUES ('9', 'SMTP_PASSWORD');
INSERT INTO `flyingbasket`.`appconfiguration` (`id`, `name`) VALUES ('10', 'SMTP_PORT');
INSERT INTO `flyingbasket`.`appconfiguration` (`id`, `name`) VALUES ('11', 'SMTP_TLS_ENABLED');
INSERT INTO `flyingbasket`.`appconfiguration` (`id`, `name`) VALUES ('12', 'SMTP_USERNAME');
INSERT INTO `flyingbasket`.`appconfiguration` (`id`, `name`) VALUES ('13', 'SMTP_EMAIL_EXEC');
INSERT INTO `flyingbasket`.`appconfiguration` (`id`, `name`, `value`) VALUES ('14', 'ANDROID_DOWNLOAD_URL', 'test url');
INSERT INTO `flyingbasket`.`appconfiguration` (`id`, `name`, `value`) VALUES ('15', 'ANDROID_VERSION', 'test');
INSERT INTO `flyingbasket`.`appconfiguration` (`id`, `name`, `value`) VALUES ('16', 'ALLOW_ALERT_NOTIFICATION', 'false');
UPDATE `flyingbasket`.`appconfiguration` SET `value`='flyingbasket.in' WHERE `id`='4';
INSERT INTO `flyingbasket`.`appconfiguration` (`id`, `name`, `value`) VALUES ('17', 'ALLOW_MAIL_NOTIFICATION', 'false');
UPDATE `flyingbasket`.`appconfiguration` SET `value`='test1' WHERE `id`='6';




------------------------------------------------------------------------------------------------
INSERT INTO `flyingbasket`.`appconfiguration` (`id`, `name`, `value`) VALUES ('7', 'ANDROID_DOWNLOAD_URL', 'http://flyingbasket.in/public_content/AN/1.0/Flyingbasket.apk');
INSERT INTO `flyingbasket`.`appconfiguration` (`id`, `name`, `value`) VALUES ('8', 'ANDROID_VERSION', '1.0');
INSERT INTO `flyingbasket`.`appconfiguration` (`id`, `name`, `value`) VALUES ('9', 'SMTP_HOST', 'smtp.gmail.com');
INSERT INTO `flyingbasket`.`appconfiguration` (`id`, `name`, `value`) VALUES ('10', 'SMTP_PORT', '25');
INSERT INTO `flyingbasket`.`appconfiguration` (`id`, `name`, `value`) VALUES ('11', 'SMTP_USERNAME', 'info@flyingbasket.in');
INSERT INTO `flyingbasket`.`appconfiguration` (`id`, `name`, `value`) VALUES ('12', 'SMTP_PASSWORD', 'Flyingbasket@2017');
INSERT INTO `flyingbasket`.`appconfiguration` (`id`, `name`, `value`) VALUES ('13', 'SMTP_TLS_ENABLED', 'true');
INSERT INTO `flyingbasket`.`appconfiguration` (`id`, `name`, `value`) VALUES ('14', 'SMTP_AUTHENTICATION', 'true');
INSERT INTO `flyingbasket`.`appconfiguration` (`id`, `name`, `value`) VALUES ('15', 'SMTP_EMAIL_EXEC', 'Flying Basket <info@flyingbasket.in>');
INSERT INTO `flyingbasket`.`appconfiguration` (`id`, `name`, `value`) VALUES ('16', 'ALLOW_ALERT_NOTIFICATION', 'true');

INSERT INTO `flyingbasket`.`appconfiguration` (`id`, `name`, `value`) VALUES ('17', 'ALLOW_SMS_NOTIFICATION', 'true');
INSERT INTO `flyingbasket`.`appconfiguration` (`id`, `name`, `value`) VALUES ('18', 'SMS_URL', 'http://103.16.101.52:8080/sendsms/bulksms');
INSERT INTO `flyingbasket`.`appconfiguration` (`id`, `name`, `value`) VALUES ('19', 'SMS_USERNAME', 'aels-nitin');
INSERT INTO `flyingbasket`.`appconfiguration` (`id`, `name`, `value`) VALUES ('20', 'SMS_PASSWORD', '123456');
INSERT INTO `flyingbasket`.`appconfiguration` (`id`, `name`, `value`) VALUES ('21', 'SMS_TYPE_ID', '0');
INSERT INTO `flyingbasket`.`appconfiguration` (`id`, `name`, `value`) VALUES ('22', 'SMS_DLR_ID', '1');
INSERT INTO `flyingbasket`.`appconfiguration` (`id`, `name`, `value`) VALUES ('23', 'SMS_FROM', 'FLKBST');
INSERT INTO `flyingbasket`.`appconfiguration` (`id`, `name`) VALUES ('24', 'SMS_COST_CENTER_ID');
INSERT INTO `flyingbasket`.`appconfiguration` (`id`, `name`) VALUES ('25', 'SMS_APP_ID');


INSERT INTO `flyingbasket`.`Users` (`id`, `address`, `is_delete`, `membership_type`, `mobile`, `name`, `password`, `reference_code`, `email_id`, `isValid`, `client_id`, `role_id`) VALUES ('1', 'GGN', '0', 'Loyality', '9540126456', 'Saurabh Aggarwal', '$2a$10$xHoy4TvYOot1/SMghe/KwO3B9MaCuhzizrjaYwxO00unjhR.boSMa', 'FLKBSK', 'saurabhaggarwal05@gmail.com', '1', '1', '1');
