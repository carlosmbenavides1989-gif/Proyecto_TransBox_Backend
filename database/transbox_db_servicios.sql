-- =============================================================
-- TRANSBOX - TABLAS REQUERIDAS POR LA EVIDENCIA DE SERVICIOS WEB
-- Evidencia GA7-220501096-AA5-EV03
-- =============================================================

CREATE DATABASE IF NOT EXISTS transbox_db
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE transbox_db;

CREATE TABLE IF NOT EXISTS roles (
    id_rol INT NOT NULL AUTO_INCREMENT,
    nombre_rol VARCHAR(50) NOT NULL,
    descripcion VARCHAR(255),
    PRIMARY KEY (id_rol),
    UNIQUE KEY uk_roles_nombre (nombre_rol)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS usuarios (
    id_usuario INT NOT NULL AUTO_INCREMENT,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    correo VARCHAR(150) NOT NULL,
    telefono VARCHAR(30),
    documento VARCHAR(50) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    estado VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE',
    fecha_creacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    id_rol INT NOT NULL,
    PRIMARY KEY (id_usuario),
    UNIQUE KEY uk_usuarios_correo (correo),
    UNIQUE KEY uk_usuarios_documento (documento),
    CONSTRAINT fk_usuarios_roles FOREIGN KEY (id_rol) REFERENCES roles(id_rol)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS pedidos (
    id_pedido INT NOT NULL AUTO_INCREMENT,
    numero_pedido VARCHAR(50) NOT NULL,
    cliente_nombre VARCHAR(150) NOT NULL,
    direccion_entrega VARCHAR(255) NOT NULL,
    descripcion VARCHAR(255),
    estado VARCHAR(30) NOT NULL DEFAULT 'PENDIENTE',
    fecha_creacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id_pedido),
    UNIQUE KEY uk_pedidos_numero (numero_pedido)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS franjas_entrega (
    id_franja INT NOT NULL AUTO_INCREMENT,
    fecha DATE NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fin TIME NOT NULL,
    cupos_disponibles INT NOT NULL,
    estado VARCHAR(20) NOT NULL DEFAULT 'DISPONIBLE',
    PRIMARY KEY (id_franja)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS vehiculos (
    id_vehiculo INT NOT NULL AUTO_INCREMENT,
    placa VARCHAR(15) NOT NULL,
    tipo VARCHAR(60) NOT NULL,
    marca VARCHAR(60),
    capacidad_kg DECIMAL(10,2),
    estado VARCHAR(30) NOT NULL DEFAULT 'DISPONIBLE',
    PRIMARY KEY (id_vehiculo),
    UNIQUE KEY uk_vehiculos_placa (placa)
) ENGINE=InnoDB;

INSERT INTO roles (id_rol, nombre_rol, descripcion)
VALUES
(1, 'Administrador', 'Administrador general del sistema'),
(2, 'Cliente', 'Cliente de la plataforma'),
(3, 'Vendedor', 'Usuario encargado de la gestión comercial'),
(4, 'Empresa', 'Empresa vinculada al proceso logístico'),
(5, 'Repartidor', 'Usuario encargado de las entregas')
ON DUPLICATE KEY UPDATE descripcion = VALUES(descripcion);
