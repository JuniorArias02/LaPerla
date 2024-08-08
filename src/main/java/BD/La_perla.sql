-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: la_perla
-- ------------------------------------------------------
-- Server version	9.0.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cliente`
--

CREATE DATABASE la_perla;
USE la_perla;

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente` (
  `codigo` int NOT NULL,
  `nombre` varchar(250) NOT NULL,
  `telefono` varchar(20) NOT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (0,'CLIENTE GENERAL','0'),(12,'yeferson SUEn','3133153114'),(123,'1232','123'),(124,'grt','12323'),(1232,'ye','12323'),(1000322243,'Cliente C','3203333333'),(1000511111,'Cliente E','3205555555'),(1002311104,'Cliente D','3204444444'),(1034422453,'Cliente B','3202222222'),(1093900012,'Cliente A','3201111111'),(1093904696,'Junior arias','312221323');
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `codigo_verificacion`
--

DROP TABLE IF EXISTS `codigo_verificacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `codigo_verificacion` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_usuario` int DEFAULT NULL,
  `codigo` varchar(6) DEFAULT NULL,
  `fecha_expiracion` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_usuario` (`id_usuario`),
  CONSTRAINT `codigo_verificacion_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `codigo_verificacion`
--

LOCK TABLES `codigo_verificacion` WRITE;
/*!40000 ALTER TABLE `codigo_verificacion` DISABLE KEYS */;
INSERT INTO `codigo_verificacion` VALUES (1,3,'878552','2024-08-07 08:41:17'),(2,3,'905033','2024-08-07 08:49:06'),(3,3,'137392','2024-08-07 08:52:21'),(4,3,'443884','2024-08-07 09:08:37'),(5,3,'303758','2024-08-07 09:26:15'),(6,3,'487764','2024-08-07 09:31:09'),(7,1,'235020','2024-08-08 19:17:40');
/*!40000 ALTER TABLE `codigo_verificacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalle_venta`
--

DROP TABLE IF EXISTS `detalle_venta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detalle_venta` (
  `codigo` int NOT NULL,
  `producto` int NOT NULL,
  `cantidad` int NOT NULL,
  KEY `producto` (`producto`),
  KEY `codigo` (`codigo`),
  CONSTRAINT `detalle_venta_ibfk_1` FOREIGN KEY (`codigo`) REFERENCES `ventas` (`codigo`),
  CONSTRAINT `detalle_venta_ibfk_2` FOREIGN KEY (`producto`) REFERENCES `productos` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle_venta`
--

LOCK TABLES `detalle_venta` WRITE;
/*!40000 ALTER TABLE `detalle_venta` DISABLE KEYS */;
INSERT INTO `detalle_venta` VALUES (2,9,0),(2,11,0),(2,11,0),(2,14,0),(3,16,0),(4,9,0),(4,9,0),(4,10,0),(4,10,0),(4,15,0),(4,16,0),(27,9,0),(28,9,2),(28,10,1),(29,9,1),(29,12,1),(30,9,4),(31,9,2),(41,9,1),(93,9,1),(94,9,2),(95,9,1),(96,9,1),(97,9,1),(98,9,1),(100,9,1),(101,9,1),(102,9,1),(103,9,1),(104,9,1),(105,9,1),(106,9,2),(106,10,2),(106,11,1),(107,9,3),(107,10,3),(107,15,2),(108,9,1),(109,9,2),(109,10,1),(109,11,1),(109,12,1);
/*!40000 ALTER TABLE `detalle_venta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productos`
--

DROP TABLE IF EXISTS `productos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productos` (
  `codigo` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(250) NOT NULL,
  `precio` int NOT NULL,
  `categoria` varchar(250) NOT NULL,
  `proveedor` int NOT NULL,
  `stock` int NOT NULL,
  PRIMARY KEY (`codigo`),
  KEY `proveedor` (`proveedor`),
  CONSTRAINT `productos_ibfk_1` FOREIGN KEY (`proveedor`) REFERENCES `proveedor` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=88173269 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productos`
--

LOCK TABLES `productos` WRITE;
/*!40000 ALTER TABLE `productos` DISABLE KEYS */;
INSERT INTO `productos` VALUES (9,'Producto 11',15000,'Electrónicos',1,50),(10,'Producto 2',25000,'Electrodomésticos',2,100),(11,'Producto 3',35000,'Ropa',3,75),(12,'Producto 4',45000,'Hogar',4,30),(13,'Producto 5',55000,'Electrónicos',5,60),(14,'Producto 6',65000,'Electrodomésticos',1,25),(15,'Producto 7',75000,'Ropa',2,80),(16,'Producto 8',85000,'Hogar',3,40),(1234,'int',1000,'int',1234,100),(513535,'thth ',150000,'eheh',3,55),(1343535,'7up',234234,'fef',12,33),(88173268,'pepsi 260ml',13443,'efe',1234,134);
/*!40000 ALTER TABLE `productos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proveedor`
--

DROP TABLE IF EXISTS `proveedor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `proveedor` (
  `codigo` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(250) NOT NULL,
  `telefono` varchar(20) NOT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=1235 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proveedor`
--

LOCK TABLES `proveedor` WRITE;
/*!40000 ALTER TABLE `proveedor` DISABLE KEYS */;
INSERT INTO `proveedor` VALUES (1,'Proveedor A','3201234567'),(2,'Proveedor B','3202345678'),(3,'Proveedor C','3203456789'),(4,'Proveedor D','3204567890'),(5,'Proveedor E','3205678901'),(12,'123','122'),(123,'tef','3'),(1234,'fefefef','121313');
/*!40000 ALTER TABLE `proveedor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id_usuario` int NOT NULL AUTO_INCREMENT,
  `usuario` varchar(30) NOT NULL,
  `nombre` varchar(30) NOT NULL,
  `gmail` varchar(250) NOT NULL,
  `clave` varchar(15) NOT NULL,
  PRIMARY KEY (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'admi','administrador','junior.arias02yt@gmail.com','123'),(3,'juniorArias','Junior Arias','junior.arias02yt@gmail.com','12345');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ventas`
--

DROP TABLE IF EXISTS `ventas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ventas` (
  `codigo` int NOT NULL AUTO_INCREMENT,
  `fecha` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `cliente` int DEFAULT NULL,
  `monto` double NOT NULL,
  PRIMARY KEY (`codigo`),
  KEY `cliente` (`cliente`),
  CONSTRAINT `ventas_ibfk_1` FOREIGN KEY (`cliente`) REFERENCES `cliente` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=110 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ventas`
--

LOCK TABLES `ventas` WRITE;
/*!40000 ALTER TABLE `ventas` DISABLE KEYS */;
INSERT INTO `ventas` VALUES (2,'2024-05-15 22:17:01',NULL,150000),(3,'2024-05-15 22:21:08',1093900012,85000),(4,'2024-05-15 22:22:50',1034422453,215000),(5,'2024-07-22 12:03:50',0,-1666),(6,'2024-07-22 12:06:28',0,-23158),(7,'2024-07-22 12:10:23',0,-33091),(8,'2024-07-22 12:15:34',0,-127125),(9,'2024-07-22 12:26:16',0,-65082303),(10,'2024-07-22 12:27:19',0,11186),(11,'2024-07-22 12:27:23',0,11186),(12,'2024-07-22 12:27:23',0,11186),(13,'2024-07-22 12:27:23',0,11186),(14,'2024-07-22 12:27:24',0,11186),(15,'2024-07-22 12:27:24',0,11186),(16,'2024-07-22 12:27:24',0,11186),(17,'2024-07-22 12:27:24',0,11186),(23,'2024-07-31 19:42:13',0,13413413),(24,'2024-07-31 19:57:36',0,-226),(25,'2024-07-31 20:37:20',0,-225),(26,'2024-07-31 20:37:44',12,-136),(27,'2024-07-31 21:05:08',12,13893),(28,'2024-07-31 21:10:47',12,53630),(29,'2024-07-31 21:18:27',12,-1310135),(30,'2024-08-02 20:25:32',0,-2615),(31,'2024-08-04 00:59:09',0,-29344),(32,'2024-08-04 01:07:35',0,0),(33,'2024-08-04 01:07:41',0,0),(34,'2024-08-04 01:07:49',0,0),(35,'2024-08-04 01:16:52',0,0),(36,'2024-08-04 01:16:54',0,0),(37,'2024-08-04 01:16:58',0,0),(38,'2024-08-04 01:20:32',0,0),(39,'2024-08-04 01:20:54',0,0),(40,'2024-08-04 01:35:32',0,0),(41,'2024-08-04 01:37:41',0,15000),(42,'2024-08-04 01:51:59',0,-14990),(43,'2024-08-04 01:52:08',0,0),(44,'2024-08-04 01:52:15',0,135001),(45,'2024-08-04 01:52:17',0,135001),(46,'2024-08-04 01:52:18',0,135001),(47,'2024-08-04 01:52:19',0,135001),(48,'2024-08-04 01:52:19',0,135001),(49,'2024-08-04 01:52:19',0,135001),(50,'2024-08-04 01:52:19',0,135001),(51,'2024-08-04 01:52:20',0,135001),(52,'2024-08-04 01:52:21',0,135001),(53,'2024-08-04 03:09:25',0,-1000),(54,'2024-08-04 03:09:29',0,125000),(55,'2024-08-04 03:09:32',0,125000),(56,'2024-08-04 03:09:33',0,125000),(57,'2024-08-04 03:11:03',0,0),(58,'2024-08-04 03:21:26',0,-1000),(59,'2024-08-04 03:21:32',0,125030),(60,'2024-08-04 03:21:34',0,125030),(61,'2024-08-04 03:21:35',0,125030),(62,'2024-08-04 03:21:35',0,125030),(63,'2024-08-04 03:21:35',0,125030),(64,'2024-08-04 03:21:36',0,125030),(65,'2024-08-04 03:21:38',0,125030),(66,'2024-08-04 03:21:38',0,125030),(67,'2024-08-04 03:21:59',0,15000),(68,'2024-08-04 03:21:59',0,15000),(69,'2024-08-04 03:21:59',0,15000),(70,'2024-08-04 03:21:59',0,15000),(71,'2024-08-04 03:22:02',0,0),(72,'2024-08-04 03:22:09',0,0),(73,'2024-08-04 03:22:22',0,1000),(74,'2024-08-04 03:22:24',0,1000),(75,'2024-08-04 03:22:25',0,1000),(76,'2024-08-04 03:22:27',0,-13400),(77,'2024-08-04 03:22:27',0,-13400),(78,'2024-08-04 03:32:07',0,-14845),(79,'2024-08-04 03:32:15',0,135005),(80,'2024-08-04 03:32:17',0,135005),(81,'2024-08-04 03:34:08',0,0),(82,'2024-08-04 03:39:20',0,0),(83,'2024-08-04 03:41:57',0,-1000),(84,'2024-08-04 03:42:08',0,125000),(85,'2024-08-04 03:43:17',0,0),(86,'2024-08-04 03:44:40',0,0),(87,'2024-08-04 03:45:47',0,0),(88,'2024-08-04 03:50:26',0,0),(89,'2024-08-04 03:50:46',0,-13500),(90,'2024-08-04 03:52:44',0,0),(91,'2024-08-04 03:57:38',0,0),(92,'2024-08-04 04:00:43',0,0),(93,'2024-08-04 04:08:01',0,0),(94,'2024-08-04 04:08:51',0,-15000),(95,'2024-08-04 04:10:52',0,-1000),(96,'2024-08-04 04:13:40',0,-13500),(97,'2024-08-04 04:15:19',0,125000),(98,'2024-08-04 04:27:31',0,125000),(99,'2024-08-04 04:28:16',0,0),(100,'2024-08-04 04:28:28',0,15000),(101,'2024-08-04 04:47:37',0,15000),(102,'2024-08-04 04:48:26',0,15000),(103,'2024-08-04 04:55:05',0,0),(104,'2024-08-04 04:58:17',0,0),(105,'2024-08-04 15:54:47',0,0),(106,'2024-08-04 17:50:18',0,0),(107,'2024-08-04 21:18:08',1093904696,0),(108,'2024-08-04 22:54:35',1093904696,15000),(109,'2024-08-05 12:36:14',1093904696,135000);
/*!40000 ALTER TABLE `ventas` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-08-08  8:23:55
