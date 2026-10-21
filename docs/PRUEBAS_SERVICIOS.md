# Guía de pruebas — TransBox API

## Preparación

1. Iniciar MySQL.
2. Importar `database/transbox_db_servicios.sql` si las tablas no existen.
3. Verificar que exista al menos un usuario activo con un rol autorizado.
4. Ejecutar:

```powershell
.\mvnw.cmd spring-boot:run
```

5. En Apidog configurar el entorno:

```text
http://localhost:8080
```

6. En la pestaña **Auth**, seleccionar **Basic Auth** e ingresar el correo y contraseña de un usuario activo.

## Casos sugeridos

### Pedido
- `POST /api/pedidos`
- `GET /api/pedidos`
- `PATCH /api/pedidos/1/estado`

### Franja
- `POST /api/franjas`
- `GET /api/franjas/disponibles`
- `PUT /api/franjas/1/reservar`

### Vehículo
- `POST /api/vehiculos`
- `GET /api/vehiculos`
- `PATCH /api/vehiculos/1/estado`

## Casos de validación

- Crear vehículo con placa repetida → `409 Conflict`.
- Crear franja con hora final anterior a la inicial → `400 Bad Request`.
- Reservar una franja sin cupos → `409 Conflict`.
- Consultar un pedido inexistente → `404 Not Found`.
- Enviar un estado no permitido → `400 Bad Request`.
