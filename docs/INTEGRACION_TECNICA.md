# Documento de integración técnica

## Arquitectura
- Frontend: React 19, Vite y React Router.
- Backend: Java 17, Spring Boot, Spring Web MVC, Spring Data JPA.
- Persistencia: MySQL.
- Seguridad: Spring Security, BCrypt y JWT.
- Documentación API: Swagger UI / OpenAPI.

## Flujo de autenticación
1. El usuario envía correo y contraseña a `POST /api/auth/login`.
2. Spring Security valida las credenciales contra la tabla `usuarios`.
3. La contraseña almacenada se compara mediante BCrypt.
4. Si son válidas, el backend emite un JWT firmado.
5. React guarda el token en `localStorage` como `sv_token`.
6. El cliente HTTP agrega `Authorization: Bearer <token>` a las solicitudes autenticadas.
7. El filtro JWT valida la firma y construye el contexto de seguridad para la solicitud.

## Autorización
- `CLIENTE`: puede autenticarse y operar endpoints protegidos para sus órdenes.
- `ADMIN`: puede administrar usuarios, productos, categorías y estados de órdenes.
- El backend es la capa de autorización efectiva. La protección de rutas React mejora la navegación, pero no reemplaza la seguridad del servidor.

## Persistencia
Las entidades JPA `Usuario`, `Producto`, `Categoria` y `Orden` se mapean a tablas MySQL. Hibernate actualiza el esquema mediante `ddl-auto=update` en el entorno local. Las contraseñas nunca se devuelven en las respuestas REST.

## Configuración
Variables:
- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`
- `JWT_SECRET`
- `JWT_EXPIRATION`
- `VITE_API_URL`

## Alcance implementado y pendientes conocidos
Implementado:
- Autenticación y registro de clientes por API.
- JWT y BCrypt.
- CRUD REST de usuarios, productos y categorías.
- Creación y consulta de órdenes.
- Restricción de rutas y operaciones por rol.
- Perfil de pruebas H2.

Pendiente de integración completa:
- Migrar/sincronizar el catálogo, categorías, usuarios y órdenes que la versión previa guardaba en `localStorage`.
- Conectar las pantallas administrativas de CRUD al API REST (actualmente varias continúan usando módulos de datos locales).
- Persistir los ítems de cada orden como detalle relacional y validar stock transaccionalmente.
- Sustituir la simulación de pago por una pasarela real, si la pauta o el alcance del proyecto lo exige.
- Ejecutar pruebas y compilación Maven en un equipo con acceso a Maven Central y validar el flujo completo con MySQL.

## Pruebas
El proyecto incluye una prueba de carga de contexto con perfil `test` y base H2 en memoria. Ejecutar `./mvnw test` desde `backend/`. La validación no pudo ejecutarse durante la preparación del paquete porque Maven Wrapper no logró descargar Maven desde Maven Central en el entorno de trabajo.
