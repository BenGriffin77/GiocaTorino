CREATE DATABASE  IF NOT EXISTS `giocatorino` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `giocatorino`;
-- MySQL dump 10.13  Distrib 5.6.13, for Win32 (x86)
--
-- Host: localhost    Database: giocatorino
-- ------------------------------------------------------
-- Server version	5.6.17

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
-- Table structure for table `boardgame_catogory`
--

DROP TABLE IF EXISTS `boardgame_catogory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `boardgame_catogory` (
  `ID_CATEGORY` int(11) NOT NULL,
  `CATEGORY_NAME` varchar(45) NOT NULL,
  PRIMARY KEY (`ID_CATEGORY`),
  KEY `CATEGORY_INDEX` (`CATEGORY_NAME`(6))
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `boardgame_catogory`
--

LOCK TABLES `boardgame_catogory` WRITE;
/*!40000 ALTER TABLE `boardgame_catogory` DISABLE KEYS */;
INSERT INTO `boardgame_catogory` VALUES (1,'Economic'),(2,'Negotiation'),(3,'Political'),(4,'Card Game'),(5,'Fantasy'),(6,'Abstract Strategy'),(7,'Medieval'),(8,'Ancient'),(9,'Civilization'),(10,'Nautical'),(11,'Exploration'),(12,'Travel'),(13,'Farming'),(14,'Mythology'),(15,'Bluffing'),(16,'Science Fiction'),(17,'Collectible Components'),(18,'Dice'),(19,'Fighting'),(20,'Print & Play'),(21,'Miniatures'),(22,'Racing'),(23,'American West'),(24,'City Building'),(25,'Adventure'),(26,'Wargame'),(27,'Space Exploration'),(28,'Renaissance'),(29,'Humor'),(30,'Electronic'),(31,'Horror'),(32,'Novel-based'),(33,'Deduction'),(34,'Word Game'),(35,'Territory Building'),(36,'Aviation / Flight'),(37,'Movies / TV / Radio theme'),(38,'Party Game'),(39,'Maze'),(40,'Puzzle'),(41,'Real-time'),(42,'Trivia'),(43,'Industry / Manufacturing'),(44,'World War II'),(45,'American Civil War'),(46,'Age of Reason'),(47,'World War I'),(48,'Trains'),(49,'Animals'),(50,'Children\'s Game'),(51,'Pirates'),(52,'Murder/Mystery'),(53,'Transportation'),(54,'Napoleonic'),(55,'Prehistoric'),(56,'Action / Dexterity'),(57,'Sports'),(58,'Game System'),(59,'Spies/Secret Agents'),(60,'Educational'),(61,'Medical'),(62,'Mafia'),(63,'Zombies'),(64,'Comic Book / Strip'),(65,'Civil War'),(66,'American Indian Wars'),(67,'American Revolutionary War'),(68,'Post-Napoleonic'),(69,'Book'),(70,'Music'),(71,'Arabian'),(72,'Memory'),(73,'Modern Warfare'),(74,'Environmental'),(75,'Number'),(76,'Religious'),(77,'Math'),(78,'Pike and Shot'),(79,'Expansion for Base-game'),(80,'Video Game Theme'),(81,'Mature / Adult'),(82,'Vietnam War'),(83,'Korean War'),(84,'Fan Expansion');
/*!40000 ALTER TABLE `boardgame_catogory` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-11-09 19:15:45
