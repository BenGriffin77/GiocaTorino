-- MySQL dump 10.13  Distrib 5.5.52, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: giocatorino
-- ------------------------------------------------------
-- Server version	5.5.52-0ubuntu0.14.04.1

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
-- Table structure for table `boardgame_status`
--

DROP TABLE IF EXISTS `boardgame_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `boardgame_status` (
  `id` smallint(4) unsigned NOT NULL AUTO_INCREMENT,
  `ID_GAME` int(11) NOT NULL,
  `OWNERID` int(11) NOT NULL,
  `STATUS` tinyint(1) DEFAULT NULL,
  `ID_EXIT` tinyint(2) DEFAULT '0',
  `LANGUAGE` varchar(45) DEFAULT NULL,
  `ID_MAINGAME` int(11) NOT NULL,
  `DEMONSTRATOR` int(11) DEFAULT '0',
  `ID_OLD_OWNER` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `OWNERID_idx` (`OWNERID`),
  KEY `DEMONSTRATORID_idx` (`DEMONSTRATOR`),
  KEY `ID_GAME_STATUS` (`ID_GAME`),
  CONSTRAINT `DEMONSTRATORID` FOREIGN KEY (`DEMONSTRATOR`) REFERENCES `users` (`USERID`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `ID_GAME_STATUS` FOREIGN KEY (`ID_GAME`) REFERENCES `boardgames` (`ID`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `OWNERID` FOREIGN KEY (`OWNERID`) REFERENCES `users` (`USERID`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `boardgame_status`
--

LOCK TABLES `boardgame_status` WRITE;
/*!40000 ALTER TABLE `boardgame_status` DISABLE KEYS */;
INSERT INTO `boardgame_status` VALUES (1,68448,191,0,0,'ITALIANO',0,0,0),(2,2651,191,0,0,'ITALIANO',0,0,0),(3,80653,191,0,0,'ITALIANO',0,0,0),(4,822,191,0,0,'ITALIANO',0,0,0),(5,478,191,0,0,'ITALIANO',0,0,0),(6,74596,191,0,0,'ITALIANO',0,0,0),(7,65244,191,0,0,'ITALIANO',0,0,0),(8,220,191,0,0,'ITALIANO',0,0,0),(9,63888,191,0,0,'ITALIANO',0,0,0),(10,73761,191,0,0,'ITALIANO',0,0,0),(11,63628,191,0,0,'ITALIANO',0,0,0),(12,10630,191,0,0,'ITALIANO',0,0,0),(13,30549,191,0,0,'ITALIANO',0,0,0),(14,30549,191,0,0,'ITALIANO',0,0,0),(15,15062,191,0,0,'ITALIANO',0,0,0),(16,157969,191,0,0,'ITALIANO',0,0,0),(17,40692,191,0,0,'ITALIANO',0,0,0),(18,42215,191,0,0,'ITALIANO',0,0,0),(19,147020,191,0,0,'ITALIANO',0,0,0),(20,68448,191,0,0,'GERMAN',0,0,0),(21,2651,191,0,0,'ITALIAN',0,0,0),(22,80653,191,0,0,'ENGLISH',0,0,0),(23,822,191,0,0,'ITALIAN',0,0,0),(24,478,191,0,0,'ENGLISH',0,0,0),(25,74596,191,0,0,'ENGLISH',0,0,0),(26,65244,191,0,0,'DUTCH',0,0,0),(27,220,191,0,0,'ENGLISH',0,0,0),(28,63888,191,0,0,'ENGLISH',0,0,0),(29,73761,191,0,0,'ENGLISH',0,0,0),(30,63628,191,0,0,'ENGLISH',0,0,0),(31,10630,191,0,0,'ENGLISH',0,0,0),(32,30549,191,0,0,'SWAHILI',0,0,0),(33,30549,191,0,0,'SWAHILI',0,0,0),(34,15062,191,0,0,'TAGALOG',0,0,0),(35,157969,191,0,0,'ENGLISH',0,0,0),(36,40692,191,0,0,'MALTESE',0,0,0),(37,42215,191,0,0,'TAGALOG',0,0,0),(38,147020,191,0,0,'ENGLISH',0,0,0),(39,68448,191,0,0,'GERMAN',0,0,0),(40,2651,191,0,0,'ITALIAN',0,0,0),(41,80653,191,0,0,'ENGLISH',0,0,0),(42,822,191,0,0,'ITALIAN',0,0,0),(43,478,191,0,0,'ENGLISH',0,0,0),(44,74596,191,0,0,'ENGLISH',0,0,0),(45,65244,191,0,0,'DUTCH',0,0,0),(46,220,191,0,0,'ENGLISH',0,0,0),(47,63888,191,0,0,'ENGLISH',0,0,0),(48,73761,191,0,0,'ENGLISH',0,0,0),(49,63628,191,0,0,'ENGLISH',0,0,0),(50,10630,191,0,0,'ENGLISH',0,0,0),(51,30549,191,0,0,'SWAHILI',0,0,0),(52,30549,191,0,0,'SWAHILI',0,0,0),(53,15062,191,0,0,'TAGALOG',0,0,0),(54,157969,191,0,0,'ENGLISH',0,0,0),(55,40692,191,0,0,'MALTESE',0,0,0),(56,42215,191,0,0,'TAGALOG',0,0,0),(57,147020,191,0,0,'ENGLISH',0,0,0),(58,68448,191,0,0,'GERMAN',0,0,0),(59,2651,191,0,0,'ITALIAN',0,0,0),(60,80653,191,0,0,'ENGLISH',0,0,0),(61,822,191,0,0,'ITALIAN',0,0,0),(62,478,191,0,0,'ENGLISH',0,0,0),(63,74596,191,0,0,'ENGLISH',0,0,0),(64,65244,191,0,0,'DUTCH',0,0,0),(65,220,191,0,0,'ENGLISH',0,0,0),(66,63888,191,0,0,'ENGLISH',0,0,0),(67,73761,191,0,0,'ENGLISH',0,0,0),(68,63628,191,0,0,'ENGLISH',0,0,0),(69,10630,191,0,0,'ENGLISH',0,0,0),(70,30549,191,0,0,'SWAHILI',0,0,0),(71,30549,191,0,0,'SWAHILI',0,0,0),(72,15062,191,0,0,'TAGALOG',0,0,0),(73,157969,191,0,0,'ENGLISH',0,0,0),(74,40692,191,0,0,'MALTESE',0,0,0),(75,42215,191,0,0,'TAGALOG',0,0,0),(76,147020,191,0,0,'ENGLISH',0,0,0);
/*!40000 ALTER TABLE `boardgame_status` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-11-05 17:25:27
