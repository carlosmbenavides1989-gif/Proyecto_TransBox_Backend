# TransBox — Diseño y desarrollo de servicios web

**Evidencia:** GA7-220501096-AA5-EV03  
**Programa:** Análisis y Desarrollo de Software (ADSO) — SENA  
**Aprendiz:** Carlos Mario Benavides Legarda  
**Ficha:** 3186642

## Descripción

Este proyecto contiene el componente backend de **TransBox**, desarrollado mediante servicios web REST para la gestión de los procesos principales de la plataforma. Incluye servicios para usuarios, roles, autenticación y seguridad, así como para los módulos de **pedidos, franjas de entrega y vehículos**.

## Arquitectura

```text
Cliente / Apidog
      |
      v
Controller REST
      |
      v
Service
      |
      v
Repository (Spring Data JPA)
      |
      v
JPA / Hibernate
      |
      v
MySQL - transbox_db
```

## Tecnologías

- Java 17
- Spring Boot
- Spring Web MVC
- Spring Data JPA
- Spring Security
- Jakarta Validation
- Thymeleaf
- MySQL
- Maven Wrapper
- Git y GitHub

## Servicios incluidos

### Usuarios y roles
- `GET /api/roles`
- `GET /api/usuarios`
- `GET /api/usuarios/{id}`
- `POST /api/usuarios`
- `PUT /api/usuarios/{id}`
- `PATCH /api/usuarios/{id}/estado`

### Pedidos
- `GET /api/pedidos`
- `GET /api/pedidos/{id}`
- `POST /api/pedidos`
- `PATCH /api/pedidos/{id}/estado`

### Franjas de entrega
- `GET /api/franjas`
- `GET /api/franjas/disponibles`
- `POST /api/franjas`
- `PUT /api/franjas/{id}/reservar`

### Vehículos
- `GET /api/vehiculos`
- `POST /api/vehiculos`
- `PATCH /api/vehiculos/{id}/estado`

### Autenticación
- `GET /login`
- `POST /login`

## Documentación de servicios

La documentación detallada se encuentra en:

```text
docs/DOCUMENTACION_SERVICIOS.md
docs/PRUEBAS_SERVICIOS.md
docs/openapi.yaml
```

El archivo `openapi.yaml` documenta formalmente los endpoints REST mediante OpenAPI 3.0.

## Base de datos

La aplicación está configurada para trabajar con:

```text
transbox_db
```

El script de apoyo para las tablas usadas en esta evidencia se encuentra en:

```text
database/transbox_db_servicios.sql
```

## Seguridad

Los servicios utilizan Spring Security. Las contraseñas se almacenan con BCrypt y el campo `passwordHash` no se devuelve en las respuestas JSON.

Para facilitar las pruebas de API se habilitan dos mecanismos de autenticación:

- formulario web personalizado;
- HTTP Basic para clientes como Apidog.

Los permisos se controlan por roles como `ADMINISTRADOR`, `VENDEDOR` y `EMPRESA`.

## Repositorio

https://github.com/carlosmbenavides1989-gif/Proyecto_TransBox_Backend

Rama principal: `main`.
