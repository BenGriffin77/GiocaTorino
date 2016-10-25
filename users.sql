-- Initial population for giocatorino.users
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
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `USERID` int(11) NOT NULL,
  `realname` char(64) DEFAULT NULL,
  `USERNAME` varchar(45) NOT NULL,
  `email` char(64) DEFAULT NULL,
  `role` tinyint(2) DEFAULT NULL,
  `available` mediumint(6) DEFAULT '0',
  `STATUS` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`USERID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (0,'','Ludoteca','info@giocatorino.it',3,63,0),(191,'','Benny','ben.griffin@shorrkan.eu',3,63,0),(192,'','Old Pig','',1,0,0),(193,'Alessio Mereu','Grugno','',2,0,0),(194,'Gianni Conrotto','Gianni C','conrotto@alice.it',1,52,0),(195,'Francesco Di Puccio','zeph','',1,0,0),(196,'Alessandro Balbo','Bifur','',3,0,0),(197,'Walter Obert','Wallover','',1,0,0),(198,'Claudio Bagliani','Tapu','claudio.bagliani@gmail.com',3,63,0),(199,'','Lycaone','',1,0,0),(200,'Edoardo Daneo','Mc Edwie','',1,0,0),(201,'','Sgrollo','',1,0,0),(202,'Fabrizio Pey','Pey','pey.fabrizio@gmx.com',3,15,0),(203,'','Mago G','mago.g@email.it',1,63,0),(204,'','Mapa','',1,0,0),(205,'Gianfranco Buccoliero','GFB','othello1967@libero.it',1,63,0),(206,'Augusto Cosi','Haug','',1,0,0),(207,'','Morsac','',1,0,0),(208,'Maurizio Banzi','Maurizio','maurizio.banzi@gmail.com',3,62,0),(209,'Diego','Diego','bontempo9@libero.it',1,56,0),(210,'Laura Garbolino','Mamma Laura','laura.garbolino@gmail.com',3,63,0),(211,'Ugo','Ugo','',1,63,0),(212,'Federico','Federico','',1,0,0),(213,'Davide Jaccod','Dunz','davide.jaccod@gmail.com',1,57,0),(214,'Loris Pereno','Loris','loris.pereno@gmail.com',1,7,0),(215,'Esteban Garbin','Este','egarbin2@gmail.com',2,0,0),(216,'Giorgio Bacolla','Kaiser','',1,0,0),(217,'Deb&Max','D&M','lamadiluce@gmail.com',1,63,0),(218,'Diego Garzena','Djago','',3,63,0),(219,'Luciano Roggero','Lucien','luciano.giocatorino@gmail.com',3,63,0),(220,'Alessandro Ruzzier','Alex Orbassano','',1,59,0),(221,'Roberto Convertino','Roberto Convertino','',1,0,0),(222,'Dario Scopacasa','Dario GiocAosta','',1,0,0),(223,'Julian','Julian GiocAosta','',1,0,0),(224,'Fabio Lamacchia','Fabio Lamacchia','',1,0,0),(225,'Nikka','Nikka GiocAosta','',1,0,0),(226,'Davide Vernassa','Redjaw','',1,0,0),(227,'Atla','Atla','',1,0,0),(228,'Giuseppe Corradetti','Casper','',1,0,0),(229,'Chiara  Giarlotto','Chiara Giarlotto','',1,0,0),(230,'Andrea Cavallo','Andrea Cavallo','',1,0,0),(231,'Valentina Sacco','Vale','',1,0,0),(232,'Dario Massarenti','Daryl74','',1,0,0),(233,'Alessio Ferraioli','Charos','',1,0,0),(234,'Aurora Costantino','Costantino','',1,0,0),(235,'Oliviero Carena','Oliviero','',1,0,0),(236,'Fabio Buccoliero','Fabio Bucco','bucco.fabio@libero.it',1,63,0),(237,'Roberto','Robyone','',1,0,0),(238,'Alessandro Notte','Taccido','ale.notte@gmail.com',3,63,0),(239,'Paola','Paola','pumapungente@yahoo.it',3,3,0),(240,'Simone Roccato','Final','',2,0,0),(241,'edoardo','edoardo','',2,0,0),(242,'Gregorio Rampa','Greg','gregoriorampa@gmail.com',3,9,0),(243,'','I Giullari','',1,10,0),(244,'Franco Rossi','Franco','',1,7,0),(245,'Sabrina','Sabry','sabrysax@gmail.com',1,63,0),(246,'Dario Schibuola','Dario il giovane','',1,5,0),(247,'','Rosy e Ale','',1,17,0),(248,'Emanuele Zulian','Doc','newdoc85@gmail.com',1,63,0),(249,'Claudia','Claudia Flower','claudia@urbanthebest.net',1,7,0);
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

-- Dump completed on 2016-10-25 16:33:58
