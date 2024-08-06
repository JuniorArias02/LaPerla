-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: tienda
-- ------------------------------------------------------
-- Server version	9.0.0

CREATE DATABASE la_perla;
USE la_perla;

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
-- Table structure for table `caja`
--



DROP TABLE IF EXISTS `caja`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `caja` (
  `caja_id` int NOT NULL AUTO_INCREMENT,
  `caja_numero` int NOT NULL,
  `caja_nombre` varchar(100) COLLATE utf8mb3_spanish2_ci NOT NULL,
  `caja_estado` varchar(17) COLLATE utf8mb3_spanish2_ci NOT NULL,
  `caja_efectivo` decimal(30,2) NOT NULL,
  PRIMARY KEY (`caja_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_spanish2_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `categoria`
--

DROP TABLE IF EXISTS `categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categoria` (
  `categoria_id` int NOT NULL AUTO_INCREMENT,
  `categoria_nombre` varchar(50) COLLATE utf8mb3_spanish2_ci NOT NULL,
  `categoria_descripcion` text COLLATE utf8mb3_spanish2_ci NOT NULL,
  `categoria_estado` varchar(20) COLLATE utf8mb3_spanish2_ci NOT NULL,
  PRIMARY KEY (`categoria_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_spanish2_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente` (
  `cliente_id` int NOT NULL AUTO_INCREMENT,
  `cliente_nombre` varchar(37) COLLATE utf8mb3_spanish2_ci NOT NULL,
  `cliente_apellido` varchar(37) COLLATE utf8mb3_spanish2_ci NOT NULL,
  `cliente_genero` varchar(10) COLLATE utf8mb3_spanish2_ci NOT NULL,
  `cliente_telefono` varchar(22) COLLATE utf8mb3_spanish2_ci NOT NULL,
  `cliente_provincia` varchar(30) COLLATE utf8mb3_spanish2_ci NOT NULL,
  `cliente_ciudad` varchar(30) COLLATE utf8mb3_spanish2_ci NOT NULL,
  `cliente_direccion` varchar(70) COLLATE utf8mb3_spanish2_ci NOT NULL,
  `cliente_email` varchar(50) COLLATE utf8mb3_spanish2_ci NOT NULL,
  `cliente_clave` varchar(535) COLLATE utf8mb3_spanish2_ci NOT NULL,
  `cliente_foto` varchar(200) COLLATE utf8mb3_spanish2_ci NOT NULL,
  `cliente_cuenta_estado` varchar(17) COLLATE utf8mb3_spanish2_ci NOT NULL,
  `cliente_cuenta_verificada` varchar(17) COLLATE utf8mb3_spanish2_ci NOT NULL,
  PRIMARY KEY (`cliente_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_spanish2_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `empresa`
--

DROP TABLE IF EXISTS `empresa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `empresa` (
  `empresa_id` int NOT NULL AUTO_INCREMENT,
  `empresa_tipo_documento` varchar(20) COLLATE utf8mb3_spanish2_ci NOT NULL,
  `empresa_numero_documento` varchar(35) COLLATE utf8mb3_spanish2_ci NOT NULL,
  `empresa_nombre` varchar(90) COLLATE utf8mb3_spanish2_ci NOT NULL,
  `empresa_telefono` varchar(20) COLLATE utf8mb3_spanish2_ci NOT NULL,
  `empresa_email` varchar(50) COLLATE utf8mb3_spanish2_ci NOT NULL,
  `empresa_direccion` varchar(100) COLLATE utf8mb3_spanish2_ci NOT NULL,
  `empresa_impuesto_nombre` varchar(10) COLLATE utf8mb3_spanish2_ci NOT NULL,
  `empresa_impuesto_porcentaje` int NOT NULL,
  `empresa_factura_impuestos` varchar(3) COLLATE utf8mb3_spanish2_ci NOT NULL,
  PRIMARY KEY (`empresa_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_spanish2_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `favorito`
--

DROP TABLE IF EXISTS `favorito`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `favorito` (
  `favorito_id` int NOT NULL AUTO_INCREMENT,
  `favorito_fecha` date NOT NULL,
  `cliente_id` int NOT NULL,
  `producto_id` int NOT NULL,
  PRIMARY KEY (`favorito_id`),
  KEY `cliente_id` (`cliente_id`),
  KEY `producto_id` (`producto_id`),
  CONSTRAINT `favorito_ibfk_1` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`producto_id`),
  CONSTRAINT `favorito_ibfk_2` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`cliente_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_spanish2_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `imagen`
--

DROP TABLE IF EXISTS `imagen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `imagen` (
  `imagen_id` int NOT NULL AUTO_INCREMENT,
  `imagen_nombre` varchar(200) COLLATE utf8mb3_spanish2_ci NOT NULL,
  `producto_id` int NOT NULL,
  PRIMARY KEY (`imagen_id`),
  KEY `producto_id` (`producto_id`),
  CONSTRAINT `imagen_ibfk_1` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`producto_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_spanish2_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `producto`
--

DROP TABLE IF EXISTS `producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `producto` (
  `producto_id` int NOT NULL AUTO_INCREMENT,
  `producto_codigo` varchar(50) COLLATE utf8mb3_spanish2_ci NOT NULL,
  `producto_sku` varchar(50) COLLATE utf8mb3_spanish2_ci NOT NULL,
  `producto_nombre` varchar(200) COLLATE utf8mb3_spanish2_ci NOT NULL,
  `producto_descripcion` varchar(535) COLLATE utf8mb3_spanish2_ci NOT NULL,
  `producto_stock` int NOT NULL,
  `producto_stock_minimo` int NOT NULL,
  `producto_precio_compra` decimal(30,2) NOT NULL,
  `producto_precio_venta` decimal(30,2) NOT NULL,
  `producto_descuento` int NOT NULL,
  `producto_tipo` varchar(10) COLLATE utf8mb3_spanish2_ci NOT NULL,
  `producto_presentacion` varchar(30) COLLATE utf8mb3_spanish2_ci NOT NULL,
  `producto_marca` varchar(50) COLLATE utf8mb3_spanish2_ci NOT NULL,
  `producto_modelo` varchar(70) COLLATE utf8mb3_spanish2_ci NOT NULL,
  `producto_estado` varchar(20) COLLATE utf8mb3_spanish2_ci NOT NULL,
  `producto_portada` varchar(300) COLLATE utf8mb3_spanish2_ci NOT NULL,
  `categoria_id` int NOT NULL,
  PRIMARY KEY (`producto_id`),
  KEY `categoria_id` (`categoria_id`),
  CONSTRAINT `producto_ibfk_1` FOREIGN KEY (`categoria_id`) REFERENCES `categoria` (`categoria_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_spanish2_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `usuario_id` int NOT NULL AUTO_INCREMENT,
  `usuario_nombre` varchar(37) COLLATE utf8mb3_spanish2_ci NOT NULL,
  `usuario_apellido` varchar(37) COLLATE utf8mb3_spanish2_ci NOT NULL,
  `usuario_telefono` varchar(22) COLLATE utf8mb3_spanish2_ci NOT NULL,
  `usuario_genero` varchar(10) COLLATE utf8mb3_spanish2_ci NOT NULL,
  `usuario_cargo` varchar(20) COLLATE utf8mb3_spanish2_ci NOT NULL,
  `usuario_usuario` varchar(30) COLLATE utf8mb3_spanish2_ci NOT NULL,
  `usuario_email` varchar(50) COLLATE utf8mb3_spanish2_ci NOT NULL,
  `usuario_clave` varchar(535) COLLATE utf8mb3_spanish2_ci NOT NULL,
  `usuario_cuenta_estado` varchar(17) COLLATE utf8mb3_spanish2_ci NOT NULL,
  `usuario_foto` varchar(200) COLLATE utf8mb3_spanish2_ci NOT NULL,
  PRIMARY KEY (`usuario_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_spanish2_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `venta`
--

DROP TABLE IF EXISTS `venta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `venta` (
  `venta_id` int NOT NULL AUTO_INCREMENT,
  `venta_codigo` varchar(200) COLLATE utf8mb3_spanish2_ci NOT NULL,
  `venta_fecha` date NOT NULL,
  `venta_hora` varchar(17) COLLATE utf8mb3_spanish2_ci NOT NULL,
  `venta_tipo_envio` varchar(20) COLLATE utf8mb3_spanish2_ci NOT NULL,
  `venta_impuesto_nombre` varchar(10) COLLATE utf8mb3_spanish2_ci NOT NULL,
  `venta_impuesto_porcentaje` int NOT NULL,
  `venta_estado_envio` varchar(20) COLLATE utf8mb3_spanish2_ci NOT NULL,
  `venta_estado_pagado` varchar(20) COLLATE utf8mb3_spanish2_ci NOT NULL,
  `venta_subtotal` decimal(30,2) NOT NULL,
  `venta_impuestos` decimal(30,2) NOT NULL,
  `venta_total` decimal(30,2) NOT NULL,
  `venta_costo` decimal(30,2) NOT NULL,
  `venta_utilidad` decimal(30,2) NOT NULL,
  `venta_pagado` decimal(30,2) NOT NULL,
  `venta_cambio` decimal(30,2) NOT NULL,
  `cliente_id` int NOT NULL,
  `usuario_id` int NOT NULL,
  `empresa_id` int NOT NULL,
  `caja_id` int NOT NULL,
  PRIMARY KEY (`venta_id`),
  UNIQUE KEY `venta_codigo` (`venta_codigo`),
  KEY `cliente_id` (`cliente_id`),
  KEY `empresa_id` (`empresa_id`),
  KEY `usuario_id` (`usuario_id`),
  KEY `caja_id` (`caja_id`),
  CONSTRAINT `venta_ibfk_1` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`cliente_id`),
  CONSTRAINT `venta_ibfk_2` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`usuario_id`),
  CONSTRAINT `venta_ibfk_3` FOREIGN KEY (`empresa_id`) REFERENCES `empresa` (`empresa_id`),
  CONSTRAINT `venta_ibfk_4` FOREIGN KEY (`caja_id`) REFERENCES `caja` (`caja_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_spanish2_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `venta_detalle`
--

DROP TABLE IF EXISTS `venta_detalle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `venta_detalle` (
  `venta_detalle_id` int NOT NULL,
  `venta_detalle_cantidad` int NOT NULL,
  `venta_detalle_precio_compra` decimal(30,2) NOT NULL,
  `venta_detalle_precio_regular` decimal(30,2) NOT NULL,
  `venta_detalle_precio_venta` decimal(30,2) NOT NULL,
  `venta_detalle_total` decimal(30,2) NOT NULL,
  `venta_detalle_costo` decimal(30,2) NOT NULL,
  `venta_detalle_utilidad` decimal(30,2) NOT NULL,
  `venta_detalle_descripcion` varchar(200) COLLATE utf8mb3_spanish2_ci NOT NULL,
  `venta_codigo` varchar(200) COLLATE utf8mb3_spanish2_ci NOT NULL,
  `producto_id` int NOT NULL,
  KEY `venta_id` (`venta_codigo`),
  KEY `producto_id` (`producto_id`),
  CONSTRAINT `venta_detalle_ibfk_2` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`producto_id`),
  CONSTRAINT `venta_detalle_ibfk_3` FOREIGN KEY (`venta_codigo`) REFERENCES `venta` (`venta_codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_spanish2_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-08-06 10:31:39
