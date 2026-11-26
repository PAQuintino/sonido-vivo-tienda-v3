# Manual de usuario – Sonido Vivo Tienda

## Cliente
1. Abrir la tienda desde `http://localhost:5173`.
2. Crear una cuenta desde **Regístrate aquí**. El sistema registra el usuario con rol `CLIENTE`.
3. Iniciar sesión con correo y contraseña.
4. Navegar por el catálogo, agregar productos al carrito y continuar al checkout.
5. El flujo de pago de esta versión continúa siendo una simulación.

## Administrador
1. Iniciar sesión con la cuenta administrativa de prueba.
2. Acceder al panel mediante la ruta `/admin`.
3. Las rutas administrativas del frontend requieren el rol `ADMIN`.
4. Las operaciones administrativas de la API también requieren autorización del backend.

## Cuentas de demostración
- Administrador: `max@duoc.cl` / `admin123`.
- Cliente: `javi@gmail.com` / `cliente123`.

Las cuentas de demostración se crean automáticamente al iniciar el backend por primera vez. Cambiar las contraseñas antes de cualquier despliegue.

## Cierre de sesión
Usar la opción **Cerrar sesión** del menú. Se elimina el JWT y la información de sesión guardada en el navegador.
