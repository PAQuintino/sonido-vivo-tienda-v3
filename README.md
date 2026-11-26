# Sonido Vivo – Tienda online de instrumentos musicales

Proyecto de la asignatura **DSY1104 Desarrollo Fullstack II** (Duoc UC), Evaluación Parcial 2.
Es el frontend de una tienda online de instrumentos musicales (guitarras, bajos, baterías,
amplificadores y pedales), construido con **React**, **Vite** y **Bootstrap**.

## Tecnologías

- React 19 + Vite
- Bootstrap 5 y react-bootstrap (diseño responsivo)
- react-router-dom (navegación entre páginas sin recargar)
- ESLint (calidad del código)
- Vitest + React Testing Library + jsdom (pruebas unitarias, por indicación docente)

## Cómo instalar y ejecutar

Requisitos: Node.js 20 o superior.

```bash
npm install        # instala las dependencias
npm run dev        # servidor de desarrollo (http://localhost:5173)
npm run build      # genera la versión de producción en dist/
npm run lint       # revisa el código con ESLint
npm test           # ejecuta las pruebas unitarias una vez (Vitest)
npm run test:watch # pruebas en modo continuo mientras programas
npm run coverage   # pruebas + reporte de cobertura (consola y carpeta coverage/)
```

## Estructura de carpetas

```
public/img/   imágenes de los productos (se sirven en /img/...)
src/          código React (Atomic Design: atoms, molecules, organisms, templates)
src/pages/    una página por cada ruta de la tienda
src/data/     "base de datos" simulada con CRUD en localStorage
src/utils/    funciones puras: validaciones, filtros, reportes
src/testUtils/ datos de ejemplo y ayudas compartidas por las pruebas
*.test.js(x)  pruebas unitarias, junto al archivo que prueban
src/context/  carrito y sesión compartidos por toda la app
legacy/       tienda original en HTML + JavaScript (se conserva solo como referencia)
```

## Rutas públicas

| Ruta | Página |
|---|---|
| `/` | Home |
| `/productos` y `/productos/:id` | Listado (con búsqueda) y detalle de producto |
| `/categorias` y `/categorias/:id` | Categorías y productos de una categoría |
| `/ofertas` | Productos en oferta |
| `/nosotros`, `/contacto` | Información y formulario de contacto |
| `/blogs` y `/blogs/:id` | Blogs y detalle de cada uno |
| `/login`, `/registro` | Inicio de sesión y registro (formularios con validaciones) |
| `/carrito` | Carrito de compras |
| `/comprar` | Checkout: datos del cliente y dirección de entrega |
| `/pago-correcto/:id` | Boleta de una compra pagada |
| `/pago-error/:id` | Pago rechazado, con botón para reintentar |
| cualquier otra | Página 404 |

## Panel de administración (solo rol `admin`)

Si alguien sin sesión (o con rol `cliente`) entra a `/admin`, se le redirige al login.
Usuario administrador de prueba: `max@duoc.cl` / `admin123`.

| Ruta | Vista |
|---|---|
| `/admin` | Dashboard: totales leídos de los datos reales y accesos rápidos |
| `/admin/ordenes` y `/admin/ordenes/:id` | Órdenes y boleta de cada orden |
| `/admin/productos` | Lista de productos (editar y eliminar) |
| `/admin/productos/nuevo` y `/admin/productos/:id/editar` | Crear y editar producto |
| `/admin/productos/criticos` | Productos con stock de 5 unidades o menos |
| `/admin/categorias`, `/nueva`, `/:id/editar` | Lista, nueva y editar categoría |
| `/admin/usuarios`, `/nuevo`, `/:id/editar` | Lista, nuevo y editar usuario |
| `/admin/usuarios/:id/historial` | Historial de compras de un usuario |
| `/admin/reportes` | Ventas totales y productos más vendidos |
| `/admin/perfil` | Datos y contraseña del administrador |

## Pruebas unitarias

Se usa **Vitest** (por indicación docente, en reemplazo de Jasmine y Karma) junto con React Testing Library,
jest-dom, user-event y jsdom. La cobertura se mide con `@vitest/coverage-v8`.

Última ejecución de `npm run coverage`: **31 archivos y 279 pruebas aprobadas**.
Cobertura: 100 % sentencias, 99,5 % ramas, 100 % funciones y 100 % líneas.

## Estado del proyecto

La migración de HTML a React está terminada: tienda pública, checkout con pago simulado y panel de
administración. La carpeta `legacy/` conserva las páginas HTML originales solo como referencia.

## Integrantes

Max Herrera, Javiera Sanches y Pablo Quintino.

## Usuarios de prueba

| Correo | Contraseña | Rol |
|---|---|---|
| `max@duoc.cl` | `admin123` | admin (al ingresar va a `/admin`) |
| `javi@gmail.com` | `cliente123` | cliente |

El pago es simulado: se aprueba con un 70 % de probabilidad (`src/utils/simularPago.js`).
Si sale "pago con error", el botón "Volver a realizar el pago" lo intenta de nuevo.


---

# Integración Backend – Evaluación DSY1104 EP3

El repositorio incorpora un backend REST en `backend/` desarrollado con Spring Boot 4, Java 17, Spring Data JPA, Spring Security, JWT y MySQL. El frontend React consume el endpoint de autenticación y registro mediante `src/services/api.js`.

## Requisitos
- Java 17 o superior.
- MySQL 8.
- Node.js 20.19+ y npm.
- Puerto 8080 disponible para Spring Boot y 5173 para Vite.

## 1. Base de datos
Crear la base de datos (o permitir que Hibernate la cree con el usuario configurado):

```sql
CREATE DATABASE sonido_vivo CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

Configurar las credenciales mediante variables de entorno `DB_URL`, `DB_USERNAME` y `DB_PASSWORD`. Los valores por defecto están en `backend/src/main/resources/application.properties` y están pensados para un MySQL local de desarrollo.

## 2. Iniciar backend
Desde la carpeta `backend`:

```bash
./mvnw spring-boot:run
```

En Windows:

```powershell
.\\mvnw.cmd spring-boot:run
```

La API queda disponible en `http://localhost:8080/api`. Swagger UI: `http://localhost:8080/swagger-ui.html`.

## 3. Iniciar frontend
En la raíz del repositorio:

```bash
npm install
npm run dev
```

La URL del backend se configura con `VITE_API_URL`. Copiar `.env.example` a `.env` si se necesita cambiar el valor.

## 4. Autenticación y cuentas de prueba
- Administrador: `max@duoc.cl` / `admin123`.
- Cliente: `javi@gmail.com` / `cliente123`.

Las cuentas se insertan al iniciar el backend si no existen. Las contraseñas se almacenan con BCrypt. El endpoint de login entrega un JWT; el frontend lo guarda en `localStorage` bajo `sv_token` y lo envía como `Authorization: Bearer <token>`.

## 5. Endpoints principales

| Método | Endpoint | Acceso | Función |
|---|---|---|---|
| POST | `/api/auth/login` | Público | Autenticar y emitir JWT |
| POST | `/api/auth/registro` | Público | Registrar cliente |
| GET | `/api/productos` | Público | Listar productos |
| GET | `/api/productos/{id}` | Público | Obtener producto |
| POST/PUT/DELETE | `/api/productos` | ADMIN | CRUD de productos |
| GET | `/api/categorias` | Público | Listar categorías |
| POST/PUT/DELETE | `/api/categorias` | ADMIN | CRUD de categorías |
| GET/POST/PUT/DELETE | `/api/usuarios` | ADMIN | CRUD de usuarios |
| GET/POST | `/api/ordenes` | Autenticado | Consultar/crear órdenes |
| PUT | `/api/ordenes/{id}/estado` | ADMIN | Actualizar estado |

## 6. Seguridad y observaciones
- Las contraseñas se codifican con BCrypt en el backend.
- Las rutas administrativas del frontend se protegen por rol y las operaciones REST se vuelven a proteger en Spring Security.
- Cambiar `JWT_SECRET` por una clave aleatoria de al menos 32 bytes antes de desplegar.
- Configurar `DB_USERNAME` y `DB_PASSWORD`; no utilizar credenciales por defecto en producción.
- Los datos históricos del `localStorage` de la versión anterior no se migran automáticamente a MySQL. Los productos/categorías que ya existan en el navegador no se sincronizan todavía con el catálogo REST.
- Las órdenes guardan total y detalle textual; no se implementó aún el detalle relacional por ítem ni la integración con una pasarela de pago real. El pago del frontend sigue siendo una simulación.
- El perfil de pruebas usa H2 en memoria y no requiere MySQL; el perfil normal requiere MySQL.
