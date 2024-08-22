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
INSERT INTO `cliente` VALUES (5,'Luis Ramírez','345678901'),(6,'Patricia Fernández','765432109'),(7,'José Gómez','456789012'),(8,'Laura Jiménez','654321098'),(9,'Miguel Torres','567890123'),(10,'Carmen Vargas','543210987'),(11,'Andrés Castillo','678901234'),(12,'Sandra Ruiz','432109876'),(13,'Jorge Mendoza','789012345'),(14,'Lucía Ortiz','321098765'),(15,'Ricardo Soto','890123456'),(16,'Natalia González','112233445'),(17,'Felipe Fernández','223344556'),(18,'Mariana López','334455667'),(19,'Eduardo Morales','445566778'),(20,'Isabel Martínez','556677889'),(21,'Gabriel Silva','667788990'),(22,'Catalina Ortega','778899001'),(23,'Santiago Pérez','889900112'),(24,'Paola Ramírez','990011223'),(25,'Carlos López','001122334'),(26,'Andrea Rodríguez','112233446'),(27,'Manuel Gómez','223344557'),(28,'Laura Salazar','334455668'),(29,'Juanita Torres','445566779'),(30,'Miguel Ángel','556677880'),(31,'Lorena Álvarez','667788991'),(32,'Oscar Ruiz','778899002'),(33,'Claudia Fernández','889900113'),(34,'Héctor González','990011224'),(35,'Ana María','001122335');
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
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `codigo_verificacion`
--

LOCK TABLES `codigo_verificacion` WRITE;
/*!40000 ALTER TABLE `codigo_verificacion` DISABLE KEYS */;
INSERT INTO `codigo_verificacion` VALUES (1,3,'878552','2024-08-07 08:41:17'),(2,3,'905033','2024-08-07 08:49:06'),(3,3,'137392','2024-08-07 08:52:21'),(4,3,'443884','2024-08-07 09:08:37'),(5,3,'303758','2024-08-07 09:26:15'),(6,3,'487764','2024-08-07 09:31:09'),(7,1,'235020','2024-08-08 19:17:40'),(8,1,'365004','2024-08-19 07:51:10'),(9,1,'022179','2024-08-19 07:54:34'),(10,1,'153733','2024-08-19 07:56:16'),(11,1,'425608','2024-08-19 07:58:03'),(12,1,'643261','2024-08-19 08:08:15'),(13,1,'297614','2024-08-19 08:10:35'),(14,1,'286572','2024-08-19 08:13:28'),(15,1,'381438','2024-08-19 08:17:03'),(16,1,'094877','2024-08-21 01:33:42'),(17,1,'449321','2024-08-21 01:55:13'),(18,1,'546609','2024-08-21 03:15:25'),(19,1,'457627','2024-08-22 07:23:17');
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
  CONSTRAINT `detalle_venta_ibfk_1` FOREIGN KEY (`codigo`) REFERENCES `ventas` (`codigo`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `detalle_venta_ibfk_2` FOREIGN KEY (`producto`) REFERENCES `productos` (`codigo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle_venta`
--

LOCK TABLES `detalle_venta` WRITE;
/*!40000 ALTER TABLE `detalle_venta` DISABLE KEYS */;
INSERT INTO `detalle_venta` VALUES (11,10,2),(11,8,1),(12,7,3),(12,6,2),(13,5,2),(13,4,4),(14,3,1),(15,9,1),(16,10,4),(16,5,2),(17,6,1),(17,4,5),(18,3,2),(18,8,3),(19,7,2),(19,9,4),(20,10,2);
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
INSERT INTO `productos` VALUES (3,'Producto C',4500,'Ropa',3,195),(4,'Producto D',7000,'Electrónica',4,50),(5,'Producto E',6000,'Ropa',5,75),(6,'Producto F',10000,'Electrónica',6,25),(7,'Producto G',8500,'Hogar',7,120),(8,'Producto H',3500,'Hogar',8,80),(9,'Producto I',5500,'Ropa',9,100),(10,'Producto J',2000,'Alimentos',10,300);
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
) ENGINE=InnoDB AUTO_INCREMENT=12346 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proveedor`
--

LOCK TABLES `proveedor` WRITE;
/*!40000 ALTER TABLE `proveedor` DISABLE KEYS */;
INSERT INTO `proveedor` VALUES (3,'Proveedor C','3344556677'),(4,'Proveedor D','4455667788'),(5,'Proveedor E','5566778899'),(6,'Proveedor F','6677889900'),(7,'Proveedor G','7788990011'),(8,'Proveedor H','8899001122'),(9,'Proveedor I','9900112233'),(10,'Proveedor J','0011223344');
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
  `clave` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'admi','administrador','junior.arias02yt@gmail.com','$2a$10$Z.xx3OOKjKc/K5jcbnFNPeZwgE6NsC81A1yRMuNGbZWG6cZUzFzz.'),(3,'juniorArias','Junior Arias','junior.arias02yt@gmail.com','$2a$10$Z.xx3OOKjKc/K5jcbnFNPeZwgE6NsC81A1yRMuNGbZWG6cZUzFzz.');
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
  CONSTRAINT `ventas_ibfk_1` FOREIGN KEY (`cliente`) REFERENCES `cliente` (`codigo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=147 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ventas`
--

LOCK TABLES `ventas` WRITE;
/*!40000 ALTER TABLE `ventas` DISABLE KEYS */;
INSERT INTO `ventas` VALUES (11,'2024-08-17 15:00:00',16,30000),(12,'2024-08-17 16:30:00',17,27000),(13,'2024-08-17 18:00:00',18,32000),(14,'2024-08-17 19:30:00',19,29000),(15,'2024-08-17 21:00:00',20,34000),(16,'2024-08-18 15:00:00',21,15000),(17,'2024-08-18 16:30:00',22,22000),(18,'2024-08-18 18:00:00',23,25000),(19,'2024-08-18 19:30:00',24,28000),(20,'2024-08-18 21:00:00',25,33000);
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

-- Dump completed on 2024-08-22 16:08:49
