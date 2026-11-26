// Guardia de rutas administrativas. La autorización real también se valida en Spring Security.
import { Navigate, Outlet } from "react-router-dom";
import { useSesion } from "../../hooks/useSesion";

export function RutaAdmin() {
  const { usuario } = useSesion();
  if (!usuario || !["ADMIN", "admin"].includes(usuario.rol)) return <Navigate to="/login" replace />;
  return <Outlet />;
}
