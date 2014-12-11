SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
-- -----------------------------------------------------
-- Schema nomina1
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `nomina1` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`historico`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`historico` ;

CREATE TABLE IF NOT EXISTS `mydb`.`historico` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id_empleado` INT NOT NULL,
  `mensaje` VARCHAR(245) NULL,
  `creado_en` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`impuesto`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`impuesto` ;

CREATE TABLE IF NOT EXISTS `mydb`.`impuesto` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `desde_salario` DOUBLE NULL,
  `hasta_salario` DOUBLE NULL,
  `factor` VARCHAR(45) NULL,
  `exceso` DOUBLE NULL,
  `nombre` VARCHAR(50) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`impuesto`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`impuesto` ;

CREATE TABLE IF NOT EXISTS `mydb`.`impuesto` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `desde_salario` DOUBLE NULL,
  `hasta_salario` DOUBLE NULL,
  `factor` VARCHAR(45) NULL,
  `exceso` DOUBLE NULL,
  `nombre` VARCHAR(50) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

USE `nomina1` ;

-- -----------------------------------------------------
-- Table `nomina1`.`cargo`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nomina1`.`cargo` ;

CREATE TABLE IF NOT EXISTS `nomina1`.`cargo` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(25) NOT NULL,
  `monto` DECIMAL(10,0) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `nomina1`.`empresa`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nomina1`.`empresa` ;

CREATE TABLE IF NOT EXISTS `nomina1`.`empresa` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(25) NULL DEFAULT NULL,
  `rnc` VARCHAR(25) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `nomina1`.`departamento`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nomina1`.`departamento` ;

CREATE TABLE IF NOT EXISTS `nomina1`.`departamento` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `id_empresa` INT(11) NULL DEFAULT NULL,
  `nombre` VARCHAR(25) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `id_empresa` (`id_empresa` ASC),
  CONSTRAINT `departamento_ibfk_1`
    FOREIGN KEY (`id_empresa`)
    REFERENCES `nomina1`.`empresa` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `nomina1`.`tipo_salario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nomina1`.`tipo_salario` ;

CREATE TABLE IF NOT EXISTS `nomina1`.`tipo_salario` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(25) NULL DEFAULT NULL,
  `factor` DECIMAL(5,2) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `nomina1`.`estados`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nomina1`.`estados` ;

CREATE TABLE IF NOT EXISTS `nomina1`.`estados` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(25) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `nomina1`.`empleado_admin`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nomina1`.`empleado_admin` ;

CREATE TABLE IF NOT EXISTS `nomina1`.`empleado_admin` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `fecha_ingreso` DATE NULL DEFAULT NULL,
  `id_departamento` INT(11) NULL DEFAULT NULL,
  `id_cargo` INT(11) NULL DEFAULT NULL,
  `tipo_salario` INT(11) NULL DEFAULT NULL,
  `id_estado` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `id_departamento` (`id_departamento` ASC),
  INDEX `id_cargo` (`id_cargo` ASC),
  INDEX `tipo_salario` (`tipo_salario` ASC),
  INDEX `id_estado` (`id_estado` ASC),
  CONSTRAINT `empleado_admin_ibfk_1`
    FOREIGN KEY (`id_departamento`)
    REFERENCES `nomina1`.`departamento` (`id`),
  CONSTRAINT `empleado_admin_ibfk_2`
    FOREIGN KEY (`id_cargo`)
    REFERENCES `nomina1`.`cargo` (`id`),
  CONSTRAINT `empleado_admin_ibfk_3`
    FOREIGN KEY (`tipo_salario`)
    REFERENCES `nomina1`.`tipo_salario` (`id`),
  CONSTRAINT `empleado_admin_ibfk_4`
    FOREIGN KEY (`id_estado`)
    REFERENCES `nomina1`.`estados` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `nomina1`.`tipos_descuentos`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nomina1`.`tipos_descuentos` ;

CREATE TABLE IF NOT EXISTS `nomina1`.`tipos_descuentos` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `nomina1`.`deduccioness_emp`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nomina1`.`deduccioness_emp` ;

CREATE TABLE IF NOT EXISTS `nomina1`.`deduccioness_emp` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `id_empleado` INT(11) NULL DEFAULT NULL,
  `tipo_deduccion` INT(11) NULL DEFAULT NULL,
  `monto` FLOAT NULL DEFAULT NULL,
  `estado` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `id_empleado` (`id_empleado` ASC),
  INDEX `tipo_deduccion` (`tipo_deduccion` ASC),
  INDEX `estado` (`estado` ASC),
  CONSTRAINT `deduccioness_emp_ibfk_1`
    FOREIGN KEY (`id_empleado`)
    REFERENCES `nomina1`.`empleado_admin` (`id`),
  CONSTRAINT `deduccioness_emp_ibfk_2`
    FOREIGN KEY (`tipo_deduccion`)
    REFERENCES `nomina1`.`tipos_descuentos` (`id`),
  CONSTRAINT `deduccioness_emp_ibfk_3`
    FOREIGN KEY (`estado`)
    REFERENCES `nomina1`.`estados` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `nomina1`.`dependientes`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nomina1`.`dependientes` ;

CREATE TABLE IF NOT EXISTS `nomina1`.`dependientes` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `id_empleado` INT(11) NULL DEFAULT NULL,
  `nombre` VARCHAR(50) NULL DEFAULT NULL,
  `apellido` VARCHAR(50) NULL DEFAULT NULL,
  `cedula` VARCHAR(11) NOT NULL,
  `tipo_dependiente` ENUM('hijo','conyuge','padre','madre','abuelo','abuela') NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `id_empleado` (`id_empleado` ASC),
  CONSTRAINT `dependientes_ibfk_1`
    FOREIGN KEY (`id_empleado`)
    REFERENCES `nomina1`.`empleado_admin` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `nomina1`.`empleado_personal`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nomina1`.`empleado_personal` ;

CREATE TABLE IF NOT EXISTS `nomina1`.`empleado_personal` (
  `id_empleado` INT NOT NULL AUTO_INCREMENT,
  `cedula` VARCHAR(11) NOT NULL,
  `nombre` VARCHAR(50) NULL DEFAULT NULL,
  `apellido` VARCHAR(50) NULL DEFAULT NULL,
  `direccion` VARCHAR(100) NULL DEFAULT NULL,
  `sexo` ENUM('m','f') NULL DEFAULT NULL,
  `estado_civil` ENUM('soltero','divorciado','casado','viudo') NULL DEFAULT NULL,
  `nacimiento` DATE NULL DEFAULT NULL,
  `email` VARCHAR(100) NULL DEFAULT NULL,
  `movil` VARCHAR(10) NULL DEFAULT NULL,
  `telefono` VARCHAR(10) NULL DEFAULT NULL,
  INDEX `id_empleado` (`id_empleado` ASC),
  PRIMARY KEY (`id_empleado`),
  CONSTRAINT `empleado_personal_ibfk_1`
    FOREIGN KEY (`id_empleado`)
    REFERENCES `nomina1`.`empleado_admin` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `nomina1`.`estado_civil`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nomina1`.`estado_civil` ;

CREATE TABLE IF NOT EXISTS `nomina1`.`estado_civil` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `tipo` VARCHAR(25) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `nomina1`.`tipos_ingresos`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nomina1`.`tipos_ingresos` ;

CREATE TABLE IF NOT EXISTS `nomina1`.`tipos_ingresos` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `nomina1`.`ingresos_emp`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nomina1`.`ingresos_emp` ;

CREATE TABLE IF NOT EXISTS `nomina1`.`ingresos_emp` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `id_empleado` INT(11) NULL DEFAULT NULL,
  `tipo_ingreso` INT(11) NULL DEFAULT NULL,
  `monto` FLOAT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `id_empleado` (`id_empleado` ASC),
  INDEX `tipo_ingreso` (`tipo_ingreso` ASC),
  CONSTRAINT `ingresos_emp_ibfk_1`
    FOREIGN KEY (`id_empleado`)
    REFERENCES `nomina1`.`empleado_admin` (`id`),
  CONSTRAINT `ingresos_emp_ibfk_2`
    FOREIGN KEY (`tipo_ingreso`)
    REFERENCES `nomina1`.`tipos_ingresos` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `nomina1`.`mes`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nomina1`.`mes` ;

CREATE TABLE IF NOT EXISTS `nomina1`.`mes` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `nomina1`.`tipo_usuarios`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nomina1`.`tipo_usuarios` ;

CREATE TABLE IF NOT EXISTS `nomina1`.`tipo_usuarios` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `nomina1`.`usuarios`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nomina1`.`usuarios` ;

CREATE TABLE IF NOT EXISTS `nomina1`.`usuarios` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `usuario` VARCHAR(20) NOT NULL,
  `clave` VARCHAR(50) NOT NULL,
  `tipo_usuario` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`usuario`),
  UNIQUE INDEX `id` (`id` ASC),
  INDEX `tipo_usuario` (`tipo_usuario` ASC),
  CONSTRAINT `usuarios_ibfk_1`
    FOREIGN KEY (`tipo_usuario`)
    REFERENCES `nomina1`.`tipo_usuarios` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `nomina1`.`usuario_pers`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nomina1`.`usuario_pers` ;

CREATE TABLE IF NOT EXISTS `nomina1`.`usuario_pers` (
  `id_usuario` VARCHAR(20) NOT NULL,
  `nombre` VARCHAR(50) NOT NULL,
  `apellido` VARCHAR(50) NOT NULL,
  `correo` VARCHAR(100) NULL DEFAULT NULL,
  INDEX `id_usuario` (`id_usuario` ASC),
  CONSTRAINT `usuario_pers_ibfk_1`
    FOREIGN KEY (`id_usuario`)
    REFERENCES `nomina1`.`usuarios` (`usuario`)
    ON DELETE CASCADE,
PRIMARY KEY (id_usuario))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

create view ultimo_empleado_id as select last_insert_id() ultimo from empleado_admin;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
