# Documentación de servicios web — TransBox

**Evidencia:** GA7-220501096-AA5-EV03  
**Base URL:** `http://localhost:8080`

## Convenciones

- Formato principal: JSON.
- Persistencia: MySQL mediante Spring Data JPA.
- Validaciones: Jakarta Validation.
- Seguridad: Spring Security.
- Para pruebas en Apidog puede utilizarse HTTP Basic con un usuario activo de la base de datos.

---

# 1. Usuarios y roles

## GET `/api/roles`
Lista los roles registrados.

**Acceso:** `ADMINISTRADOR`.

**Respuesta esperada:** `200 OK`.

---

## GET `/api/usuarios`
Lista todos los usuarios.

**Acceso:** `ADMINISTRADOR`.

**Respuesta esperada:** `200 OK`.

---

## GET `/api/usuarios/{id}`
Busca un usuario por identificador.

**Parámetro:** `id` entero.

**Respuestas:**
- `200 OK`: usuario encontrado.
- `404 Not Found`: usuario inexistente.

---

## POST `/api/usuarios`
Registra un usuario nuevo.

**Body:**

```json
{
  "nombres": "Laura",
  "apellidos": "Rivas",
  "correo": "laura@transbox.com",
  "telefono": "3100000000",
  "documento": "100200300",
  "contrasena": "ClaveSegura123"
}
```

**Reglas:**
- correo y documento no pueden repetirse;
- la contraseña debe tener mínimo 12 caracteres;
- se cifra con BCrypt;
- el usuario inicia en estado `PENDIENTE`;
- se asigna el rol `Vendedor`.

**Respuestas:** `201`, `400`, `409`, `500`.

---

## PUT `/api/usuarios/{id}`
Actualiza nombres, apellidos y teléfono.

```json
{
  "nombres": "Carlos Mario",
  "apellidos": "Benavides Legarda",
  "telefono": "3208116379"
}
```

**Respuestas:** `200`, `400`, `404`.

---

## PATCH `/api/usuarios/{id}/estado`
Cambia el estado de un usuario.

```json
{
  "estado": "ACTIVO"
}
```

**Estados válidos:** `ACTIVO`, `INACTIVO`, `PENDIENTE`.

---

# 2. Pedidos

## GET `/api/pedidos`
Lista los pedidos registrados.

**Acceso:** `ADMINISTRADOR`, `VENDEDOR` o `EMPRESA`.

---

## GET `/api/pedidos/{id}`
Consulta un pedido específico.

**Respuesta:** `200` o `404`.

---

## POST `/api/pedidos`
Crea un pedido y genera automáticamente un número con formato `TBX-AAAAMMDD-####`.

```json
{
  "clienteNombre": "Cliente demostración",
  "direccionEntrega": "Carrera 10 # 20-30, Bogotá",
  "descripcion": "Caja de repuestos"
}
```

**Estado inicial:** `PENDIENTE`.

**Respuesta:** `201 Created`.

---

## PATCH `/api/pedidos/{id}/estado`
Actualiza el estado operativo de un pedido.

```json
{
  "estado": "EN_RUTA"
}
```

**Estados válidos:**
- `PENDIENTE`
- `CONFIRMADO`
- `EN_RUTA`
- `ENTREGADO`
- `SIN_RESPUESTA`
- `RETORNADO`

---

# 3. Franjas de entrega

## GET `/api/franjas`
Lista todas las franjas.

---

## GET `/api/franjas/disponibles`
Lista únicamente las franjas con estado `DISPONIBLE` y al menos un cupo.

---

## POST `/api/franjas`
Crea una nueva franja.

```json
{
  "fecha": "2026-10-21",
  "horaInicio": "08:00:00",
  "horaFin": "10:00:00",
  "cuposDisponibles": 5
}
```

**Regla:** la hora final debe ser posterior a la hora inicial.

**Estado inicial:** `DISPONIBLE`.

---

## PUT `/api/franjas/{id}/reservar`
Reserva un cupo de la franja.

El servicio disminuye `cuposDisponibles`. Cuando llega a cero cambia el estado a `LLENA`.

**Respuestas:** `200`, `404`, `409`.

---

# 4. Vehículos

## GET `/api/vehiculos`
Lista los vehículos registrados.

**Acceso:** `ADMINISTRADOR` o `EMPRESA`.

---

## POST `/api/vehiculos`
Registra un vehículo.

```json
{
  "placa": "ABC123",
  "tipo": "Camión",
  "marca": "Chevrolet",
  "capacidadKg": 3500.00
}
```

**Reglas:**
- la placa es obligatoria y única;
- el tipo es obligatorio;
- la capacidad, si se informa, debe ser mayor a cero.

**Estado inicial:** `DISPONIBLE`.

**Respuestas:** `201`, `400`, `409`.

---

## PATCH `/api/vehiculos/{id}/estado`
Cambia el estado del vehículo.

```json
{
  "estado": "MANTENIMIENTO"
}
```

**Estados válidos:** `DISPONIBLE`, `ASIGNADO`, `MANTENIMIENTO`, `INACTIVO`.

---

# 5. Autenticación

## GET `/login`
Muestra el formulario personalizado de inicio de sesión.

## POST `/login`
Procesado por Spring Security. Utiliza el correo como nombre de usuario y valida la contraseña BCrypt.

También se habilita HTTP Basic para facilitar la validación de los servicios REST en Apidog.
