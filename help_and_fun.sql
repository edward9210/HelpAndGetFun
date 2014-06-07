-- MySQL dump 10.13  Distrib 5.6.10, for Win64 (x86_64)
--
-- Host: localhost    Database: help_and_fun
-- ------------------------------------------------------
-- Server version	5.6.10

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
-- Table structure for table `cre_par`
--

DROP TABLE IF EXISTS `cre_par`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cre_par` (
  `user` varchar(45) NOT NULL,
  `mission` varchar(45) NOT NULL,
  `cr_or_pr` int(11) DEFAULT '0' COMMENT '0 for create and 1 for participate',
  PRIMARY KEY (`user`,`mission`),
  KEY `userp_idx` (`user`),
  KEY `missionp_idx` (`mission`),
  CONSTRAINT `missionp` FOREIGN KEY (`mission`) REFERENCES `mission` (`missionname`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `userp` FOREIGN KEY (`user`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cre_par`
--

LOCK TABLES `cre_par` WRITE;
/*!40000 ALTER TABLE `cre_par` DISABLE KEYS */;
INSERT INTO `cre_par` VALUES ('Edward','外卖，酸辣土豆丝',0),('Edward','拿快递',0),('luxuguang','买瓶水',0),('luxuguang','拿快递',1);
/*!40000 ALTER TABLE `cre_par` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `is_friend`
--

DROP TABLE IF EXISTS `is_friend`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `is_friend` (
  `user1` varchar(45) NOT NULL,
  `user2` varchar(45) NOT NULL,
  PRIMARY KEY (`user1`,`user2`),
  KEY `u1_idx` (`user1`),
  KEY `u2_idx` (`user2`),
  CONSTRAINT `u1` FOREIGN KEY (`user1`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `u2` FOREIGN KEY (`user2`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `is_friend`
--

LOCK TABLES `is_friend` WRITE;
/*!40000 ALTER TABLE `is_friend` DISABLE KEYS */;
/*!40000 ALTER TABLE `is_friend` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mission`
--

DROP TABLE IF EXISTS `mission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mission` (
  `missionname` varchar(45) NOT NULL DEFAULT '0',
  `missiontime` datetime DEFAULT NULL,
  `missiondeadline` datetime DEFAULT NULL,
  `missionplace` varchar(45) DEFAULT NULL,
  `missionps` varchar(45) DEFAULT NULL,
  `missionstate` int(11) DEFAULT '0',
  PRIMARY KEY (`missionname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mission`
--

LOCK TABLES `mission` WRITE;
/*!40000 ALTER TABLE `mission` DISABLE KEYS */;
INSERT INTO `mission` VALUES ('买瓶水','2014-06-06 11:32:02','2014-06-07 10:30:02','教育超市','要冷的',0),('外卖，酸辣土豆丝','2014-06-06 11:28:16','2014-06-07 12:00:16','三饭','加多杯可乐',0),('拿快递','2014-06-06 11:30:14','2014-06-08 01:10:14','新天地','',1);
/*!40000 ALTER TABLE `mission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `username` varchar(45) NOT NULL DEFAULT '0',
  `realname` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('Edward','吴国诚','12345','13580515507'),('luxuguang','老卢','12345','13580888888'),('xubin1','许彬','12345','13580987654');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-06-07 16:18:14
