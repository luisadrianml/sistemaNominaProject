-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: 127.0.0.1    Database: nomina1
-- ------------------------------------------------------
-- Server version	5.6.16

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
-- Table structure for table `cargo`
--

DROP TABLE IF EXISTS `cargo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cargo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(25) NOT NULL,
  `monto` decimal(10,0) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cargo`
--

LOCK TABLES `cargo` WRITE;
/*!40000 ALTER TABLE `cargo` DISABLE KEYS */;
INSERT INTO `cargo` VALUES (1,'CTO.1',400000),(2,'CTO.2',500000),(3,'CTO.3',600000),(4,'CTO.4',700000),(5,'CTO.5',800000),(6,'CTO.6',900000),(7,'CTO.7',1000000),(8,'CEO',1112304),(9,'Programador',40000),(10,'Programador II',16000),(11,'Programador III',35000);
/*!40000 ALTER TABLE `cargo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `deduccioness_emp`
--

DROP TABLE IF EXISTS `deduccioness_emp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `deduccioness_emp` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_empleado` int(11) DEFAULT NULL,
  `tipo_deduccion` int(11) DEFAULT NULL,
  `monto` float DEFAULT NULL,
  `estado` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_empleado` (`id_empleado`),
  KEY `tipo_deduccion` (`tipo_deduccion`),
  KEY `estado` (`estado`),
  CONSTRAINT `deduccioness_emp_ibfk_1` FOREIGN KEY (`id_empleado`) REFERENCES `empleado_admin` (`id`),
  CONSTRAINT `deduccioness_emp_ibfk_2` FOREIGN KEY (`tipo_deduccion`) REFERENCES `tipos_descuentos` (`id`),
  CONSTRAINT `deduccioness_emp_ibfk_3` FOREIGN KEY (`estado`) REFERENCES `estados` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `deduccioness_emp`
--

LOCK TABLES `deduccioness_emp` WRITE;
/*!40000 ALTER TABLE `deduccioness_emp` DISABLE KEYS */;
INSERT INTO `deduccioness_emp` VALUES (2,4,1,500,1),(9,10,3,333,1),(11,3,3,245.123,1),(12,13,3,2500,1);
/*!40000 ALTER TABLE `deduccioness_emp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `departamento`
--

DROP TABLE IF EXISTS `departamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `departamento` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_empresa` int(11) DEFAULT NULL,
  `nombre` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_empresa` (`id_empresa`),
  CONSTRAINT `departamento_ibfk_1` FOREIGN KEY (`id_empresa`) REFERENCES `empresa` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `departamento`
--

LOCK TABLES `departamento` WRITE;
/*!40000 ALTER TABLE `departamento` DISABLE KEYS */;
INSERT INTO `departamento` VALUES (1,2,'IT'),(2,2,'Recursos'),(3,2,'Analista de sistemas'),(4,2,'Gerencia'),(5,2,'Soporte tecnico'),(6,2,'Contabilidad'),(7,2,'Gerencia ejecutiva');
/*!40000 ALTER TABLE `departamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dependientes`
--

DROP TABLE IF EXISTS `dependientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dependientes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_empleado` int(11) DEFAULT NULL,
  `nombre` varchar(50) DEFAULT NULL,
  `apellido` varchar(50) DEFAULT NULL,
  `cedula` varchar(11) NOT NULL,
  `tipo_dependiente` enum('hijo','conyuge','padre','madre','abuelo','abuela') DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_empleado` (`id_empleado`),
  CONSTRAINT `dependientes_ibfk_1` FOREIGN KEY (`id_empleado`) REFERENCES `empleado_admin` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dependientes`
--

LOCK TABLES `dependientes` WRITE;
/*!40000 ALTER TABLE `dependientes` DISABLE KEYS */;
INSERT INTO `dependientes` VALUES (2,5,'Maria','Contreras','44422220364','conyuge'),(3,5,'Maria','Contreras','44422220364','conyuge'),(4,5,'Maria','Contreras','44422220364','conyuge'),(5,8,'Juanny','Gutierrez','','hijo'),(6,9,'Fanny','Maria','','abuela'),(7,10,'sss','sssss','','abuela'),(8,11,'Marcos','Perez','','abuelo'),(9,12,'Padre','Santana','40233000123','padre'),(10,13,'Juana','De Marichal','40222036217','conyuge');
/*!40000 ALTER TABLE `dependientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empleado_admin`
--

DROP TABLE IF EXISTS `empleado_admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `empleado_admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fecha_ingreso` date DEFAULT NULL,
  `id_departamento` int(11) DEFAULT NULL,
  `id_cargo` int(11) DEFAULT NULL,
  `tipo_salario` int(11) DEFAULT NULL,
  `id_estado` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_departamento` (`id_departamento`),
  KEY `id_cargo` (`id_cargo`),
  KEY `tipo_salario` (`tipo_salario`),
  KEY `id_estado` (`id_estado`),
  CONSTRAINT `empleado_admin_ibfk_1` FOREIGN KEY (`id_departamento`) REFERENCES `departamento` (`id`),
  CONSTRAINT `empleado_admin_ibfk_2` FOREIGN KEY (`id_cargo`) REFERENCES `cargo` (`id`),
  CONSTRAINT `empleado_admin_ibfk_3` FOREIGN KEY (`tipo_salario`) REFERENCES `tipo_salario` (`id`),
  CONSTRAINT `empleado_admin_ibfk_4` FOREIGN KEY (`id_estado`) REFERENCES `estados` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empleado_admin`
--

LOCK TABLES `empleado_admin` WRITE;
/*!40000 ALTER TABLE `empleado_admin` DISABLE KEYS */;
INSERT INTO `empleado_admin` VALUES (2,'2010-03-02',1,1,1,1),(3,'2008-05-01',1,1,1,1),(4,'2014-12-11',1,1,1,1),(5,'2014-12-11',1,4,1,1),(6,'2014-12-11',1,4,1,1),(7,'2014-12-11',1,4,1,1),(8,'2011-12-01',1,4,1,2),(9,'2014-12-11',1,2,1,1),(10,'2014-12-12',2,6,1,1),(11,'2014-11-12',7,9,2,1),(12,'2014-12-15',5,11,3,1),(13,'2014-10-15',4,10,2,1);
/*!40000 ALTER TABLE `empleado_admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empleado_personal`
--

DROP TABLE IF EXISTS `empleado_personal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `empleado_personal` (
  `id_empleado` int(11) NOT NULL AUTO_INCREMENT,
  `cedula` varchar(11) NOT NULL,
  `nombre` varchar(50) DEFAULT NULL,
  `apellido` varchar(50) DEFAULT NULL,
  `direccion` varchar(100) DEFAULT NULL,
  `sexo` enum('m','f') DEFAULT NULL,
  `estado_civil` enum('soltero','divorciado','casado','viudo') DEFAULT NULL,
  `nacimiento` date DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `movil` varchar(10) DEFAULT NULL,
  `telefono` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id_empleado`),
  KEY `id_empleado` (`id_empleado`),
  CONSTRAINT `empleado_personal_ibfk_1` FOREIGN KEY (`id_empleado`) REFERENCES `empleado_admin` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empleado_personal`
--

LOCK TABLES `empleado_personal` WRITE;
/*!40000 ALTER TABLE `empleado_personal` DISABLE KEYS */;
INSERT INTO `empleado_personal` VALUES (4,'40222036525','Ana','Maria','Av. Kennedy','f','casado','2012-01-13','juan@outlook.com','8291112211','8293331122'),(5,'44455236587','Juan','Perez','Su casa','m','soltero','2008-12-25','fsdfsd@gmail.com','8297778888','7879998888'),(8,'45678984578','Anita','Rosario','John','f','divorciado','2000-11-28','1ama@hotmail.com','2','1'),(9,'40222036297','Luis','Martinez','Mi casa','m','soltero','2014-08-18','121@outlook.com','7894561231','7894561323'),(10,'455556327','Luis','Martinez','sss','m','soltero','2014-12-02','ssss','7894561231','7894561231'),(11,'40211111111','Fernando','Perez','Casa domicilio','m','soltero','1994-05-25','luismartinez@gmail.com','1111111111','2222222222'),(12,'40222036291','Aner','Santana','Villa mella','m','soltero','2014-11-04','aner@santana.com','8097894561','8291234567'),(13,'40222036278','Juan','Marichal','Av. Bajo puente','m','soltero','2000-02-18','ejemplo@hora.com','7894561231','7894561231');
/*!40000 ALTER TABLE `empleado_personal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empresa`
--

DROP TABLE IF EXISTS `empresa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `empresa` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(25) DEFAULT NULL,
  `rnc` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empresa`
--

LOCK TABLES `empresa` WRITE;
/*!40000 ALTER TABLE `empresa` DISABLE KEYS */;
INSERT INTO `empresa` VALUES (2,'CROOM','101027794');
/*!40000 ALTER TABLE `empresa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estado_civil`
--

DROP TABLE IF EXISTS `estado_civil`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `estado_civil` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tipo` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estado_civil`
--

LOCK TABLES `estado_civil` WRITE;
/*!40000 ALTER TABLE `estado_civil` DISABLE KEYS */;
/*!40000 ALTER TABLE `estado_civil` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estados`
--

DROP TABLE IF EXISTS `estados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `estados` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estados`
--

LOCK TABLES `estados` WRITE;
/*!40000 ALTER TABLE `estados` DISABLE KEYS */;
INSERT INTO `estados` VALUES (1,'activo'),(2,'inactivo');
/*!40000 ALTER TABLE `estados` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `historico`
--

DROP TABLE IF EXISTS `historico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `historico` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_empleado` int(11) NOT NULL,
  `mensaje` varchar(245) DEFAULT NULL,
  `creado_en` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `historico`
--

LOCK TABLES `historico` WRITE;
/*!40000 ALTER TABLE `historico` DISABLE KEYS */;
/*!40000 ALTER TABLE `historico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `historico_general`
--

DROP TABLE IF EXISTS `historico_general`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `historico_general` (
  `id_empleado` int(11) DEFAULT NULL,
  `salariobruto` decimal(15,2) DEFAULT NULL,
  `sumaIngresos` decimal(15,2) DEFAULT NULL,
  `ingresoMasSalario` decimal(15,2) DEFAULT NULL,
  `horasExtra` decimal(15,2) DEFAULT NULL,
  `sumaImpuestos` decimal(15,2) DEFAULT NULL,
  `deducciones` decimal(15,2) DEFAULT NULL,
  `sumaDeducciones` decimal(15,2) DEFAULT NULL,
  `salarioNeto` decimal(15,2) DEFAULT NULL,
  `mes` int(2) DEFAULT NULL,
  `ano` varchar(4) DEFAULT NULL,
  `periodo` int(11) DEFAULT NULL,
  KEY `id_empleado` (`id_empleado`),
  CONSTRAINT `historico_general_ibfk_1` FOREIGN KEY (`id_empleado`) REFERENCES `empleado_admin` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `historico_general`
--

LOCK TABLES `historico_general` WRITE;
/*!40000 ALTER TABLE `historico_general` DISABLE KEYS */;
INSERT INTO `historico_general` VALUES (11,40000.00,0.00,20000.00,2000.00,NULL,NULL,NULL,NULL,1,'2014',1),(11,40000.00,0.00,40000.00,2000.00,3310.36,0.00,3310.36,38689.64,1,'2014',2),(5,700000.00,250.00,700250.00,5000.00,196381.02,0.00,196381.02,508868.98,1,'2014',1),(12,35000.00,1500.00,10250.00,0.00,NULL,NULL,NULL,NULL,2,'2014',1),(11,40000.00,0.00,20000.00,0.00,NULL,NULL,NULL,NULL,2,'2014',1),(5,700000.00,250.00,700250.00,5000.00,196381.02,0.00,196381.02,508868.98,2,'2014',1);
/*!40000 ALTER TABLE `historico_general` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `historico_general_todos`
--

DROP TABLE IF EXISTS `historico_general_todos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `historico_general_todos` (
  `id_empleado` int(11) DEFAULT NULL,
  `salariobruto` decimal(15,2) DEFAULT NULL,
  `sumaIngresos` decimal(15,2) DEFAULT NULL,
  `ingresoMasSalario` decimal(15,2) DEFAULT NULL,
  `horasExtra` decimal(15,2) DEFAULT NULL,
  `sumaImpuestos` decimal(15,2) DEFAULT NULL,
  `deducciones` decimal(15,2) DEFAULT NULL,
  `sumaDeducciones` decimal(15,2) DEFAULT NULL,
  `salarioNeto` decimal(15,2) DEFAULT NULL,
  `mes` int(2) DEFAULT NULL,
  `ano` varchar(4) DEFAULT NULL,
  `periodo` int(11) DEFAULT NULL,
  KEY `id_empleado` (`id_empleado`),
  CONSTRAINT `historico_general_todos_ibfk_1` FOREIGN KEY (`id_empleado`) REFERENCES `empleado_admin` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `historico_general_todos`
--

LOCK TABLES `historico_general_todos` WRITE;
/*!40000 ALTER TABLE `historico_general_todos` DISABLE KEYS */;
INSERT INTO `historico_general_todos` VALUES (4,400000.00,1500.00,401500.00,0.00,107201.42,500.00,107701.42,293798.58,2,'2014',1),(5,700000.00,1750.00,701750.00,0.00,195572.50,0.00,195572.50,506177.50,2,'2014',1),(9,500000.00,0.00,500000.00,0.00,136192.44,3345.40,139537.84,360462.16,2,'2014',1),(10,900000.00,0.00,900000.00,0.00,253922.44,333.00,254255.44,645744.56,2,'2014',1),(4,400000.00,1500.00,401500.00,0.00,107201.42,500.00,107701.42,293798.58,4,'2014',1),(5,700000.00,1750.00,701750.00,0.00,195572.50,0.00,195572.50,506177.50,4,'2014',1),(9,500000.00,0.00,500000.00,0.00,136192.44,3345.40,139537.84,360462.16,4,'2014',1),(10,900000.00,0.00,900000.00,0.00,253922.44,333.00,254255.44,645744.56,4,'2014',1),(4,400000.00,1500.00,401500.00,0.00,107201.42,500.00,107701.42,293798.58,2,'2014',1),(5,700000.00,250.00,700250.00,5000.00,196381.02,0.00,196381.02,508868.98,2,'2014',1),(9,500000.00,0.00,500000.00,0.00,136192.44,0.00,136192.44,363807.56,2,'2014',1),(10,900000.00,0.00,900000.00,0.00,253922.44,333.00,254255.44,645744.56,2,'2014',1);
/*!40000 ALTER TABLE `historico_general_todos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `impuesto`
--

DROP TABLE IF EXISTS `impuesto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `impuesto` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `desde_salario` double DEFAULT NULL,
  `hasta_salario` double DEFAULT NULL,
  `factor` varchar(45) DEFAULT NULL,
  `exceso` double DEFAULT NULL,
  `nombre` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `impuesto`
--

LOCK TABLES `impuesto` WRITE;
/*!40000 ALTER TABLE `impuesto` DISABLE KEYS */;
INSERT INTO `impuesto` VALUES (1,NULL,NULL,'0.0304',NULL,'SFS'),(2,NULL,NULL,'0.0287',NULL,'AFP'),(3,0,399923,'0',NULL,'ISR-Primer Rango'),(4,399923.01,599884,'0.15',NULL,'ISR-Segundo Rango'),(5,599884.01,833171,'0.20',29994,'ISR-Tercer Rango'),(6,833171.01,0,'0.25',76652,'ISR-Cuarto Rango');
/*!40000 ALTER TABLE `impuesto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ingresos_emp`
--

DROP TABLE IF EXISTS `ingresos_emp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ingresos_emp` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_empleado` int(11) DEFAULT NULL,
  `tipo_ingreso` int(11) DEFAULT NULL,
  `monto` float DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_empleado` (`id_empleado`),
  KEY `tipo_ingreso` (`tipo_ingreso`),
  CONSTRAINT `ingresos_emp_ibfk_1` FOREIGN KEY (`id_empleado`) REFERENCES `empleado_admin` (`id`),
  CONSTRAINT `ingresos_emp_ibfk_2` FOREIGN KEY (`tipo_ingreso`) REFERENCES `tipos_ingresos` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ingresos_emp`
--

LOCK TABLES `ingresos_emp` WRITE;
/*!40000 ALTER TABLE `ingresos_emp` DISABLE KEYS */;
INSERT INTO `ingresos_emp` VALUES (1,3,1,500),(3,5,1,250),(10,6,2,14000),(12,4,3,1500),(21,13,1,1000),(22,12,3,1500),(23,5,2,5000);
/*!40000 ALTER TABLE `ingresos_emp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mes`
--

DROP TABLE IF EXISTS `mes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mes`
--

LOCK TABLES `mes` WRITE;
/*!40000 ALTER TABLE `mes` DISABLE KEYS */;
/*!40000 ALTER TABLE `mes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_salario`
--

DROP TABLE IF EXISTS `tipo_salario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipo_salario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(25) DEFAULT NULL,
  `factor` decimal(5,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_salario`
--

LOCK TABLES `tipo_salario` WRITE;
/*!40000 ALTER TABLE `tipo_salario` DISABLE KEYS */;
INSERT INTO `tipo_salario` VALUES (1,'mensual',23.83),(2,'quincenal',11.91),(3,'semana',5.50),(4,'hora',8.00);
/*!40000 ALTER TABLE `tipo_salario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_usuarios`
--

DROP TABLE IF EXISTS `tipo_usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipo_usuarios` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_usuarios`
--

LOCK TABLES `tipo_usuarios` WRITE;
/*!40000 ALTER TABLE `tipo_usuarios` DISABLE KEYS */;
INSERT INTO `tipo_usuarios` VALUES (1,'administrador'),(2,'contable');
/*!40000 ALTER TABLE `tipo_usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipos_descuentos`
--

DROP TABLE IF EXISTS `tipos_descuentos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipos_descuentos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipos_descuentos`
--

LOCK TABLES `tipos_descuentos` WRITE;
/*!40000 ALTER TABLE `tipos_descuentos` DISABLE KEYS */;
INSERT INTO `tipos_descuentos` VALUES (1,'Deduccion 1'),(3,'Sobregiro de tarjeta'),(4,'Prestamo');
/*!40000 ALTER TABLE `tipos_descuentos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipos_ingresos`
--

DROP TABLE IF EXISTS `tipos_ingresos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipos_ingresos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipos_ingresos`
--

LOCK TABLES `tipos_ingresos` WRITE;
/*!40000 ALTER TABLE `tipos_ingresos` DISABLE KEYS */;
INSERT INTO `tipos_ingresos` VALUES (1,'Ingreso 1'),(2,'HORAS_EXTRAS'),(3,'Farmacia'),(6,'Gasolina'),(7,'Comision'),(8,'Ingresos 3');
/*!40000 ALTER TABLE `tipos_ingresos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `ultimo_empleado_id`
--

DROP TABLE IF EXISTS `ultimo_empleado_id`;
/*!50001 DROP VIEW IF EXISTS `ultimo_empleado_id`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `ultimo_empleado_id` (
  `ultimo` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `usuario_pers`
--

DROP TABLE IF EXISTS `usuario_pers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario_pers` (
  `id_usuario` varchar(20) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `apellido` varchar(50) NOT NULL,
  `correo` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id_usuario`),
  KEY `id_usuario` (`id_usuario`),
  CONSTRAINT `usuario_pers_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`usuario`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario_pers`
--

LOCK TABLES `usuario_pers` WRITE;
/*!40000 ALTER TABLE `usuario_pers` DISABLE KEYS */;
INSERT INTO `usuario_pers` VALUES ('admin','Luis','Martinez','admin@hotmail.com'),('contable','contable','contable','contable@cb.com');
/*!40000 ALTER TABLE `usuario_pers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `usuario` varchar(20) NOT NULL,
  `clave` varchar(50) NOT NULL,
  `tipo_usuario` int(11) DEFAULT NULL,
  PRIMARY KEY (`usuario`),
  UNIQUE KEY `id` (`id`),
  KEY `tipo_usuario` (`tipo_usuario`),
  CONSTRAINT `usuarios_ibfk_1` FOREIGN KEY (`tipo_usuario`) REFERENCES `tipo_usuarios` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (13,'admin','admin',1),(24,'contable','contable',2);
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Final view structure for view `ultimo_empleado_id`
--

/*!50001 DROP TABLE IF EXISTS `ultimo_empleado_id`*/;
/*!50001 DROP VIEW IF EXISTS `ultimo_empleado_id`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `ultimo_empleado_id` AS select last_insert_id() AS `ultimo` from `empleado_admin` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-12-17  1:11:20
