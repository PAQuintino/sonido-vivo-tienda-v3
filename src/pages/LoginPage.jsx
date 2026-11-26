// Inicio de sesión contra el backend: las credenciales se validan en Spring Security.
import { useState } from "react";
import { Alert } from "react-bootstrap";
import { Link, useLocation, useNavigate } from "react-router-dom";
import { FormularioLogin } from "../components/organisms/FormularioLogin";
import { useSesion } from "../hooks/useSesion";

export function LoginPage() {
  const { iniciarSesion } = useSesion();
  const navigate = useNavigate();
  const location = useLocation();
  const [errorGeneral, setErrorGeneral] = useState("");
  const [cargando, setCargando] = useState(false);
  const registroExitoso = location.state?.registroExitoso === true;

  const ingresar = async ({ correo, password }) => {
    setCargando(true);
    const usuario = await iniciarSesion(correo, password);
    setCargando(false);
    if (!usuario) {
      setErrorGeneral("No fue posible iniciar sesión. Verifica tus credenciales y que el backend esté conectado.");
      return;
    }
    navigate(usuario.rol === "ADMIN" || usuario.rol === "admin" ? "/admin" : "/");
  };

  return (
    <div className="mx-auto" style={{ maxWidth: "500px" }}>
      <h1 className="mb-1">Iniciar sesión</h1>
      <p className="text-muted">Ingresa a tu cuenta para gestionar tus compras.</p>
      {registroExitoso && <Alert variant="success">¡Registro completado con éxito! Ahora puedes iniciar sesión.</Alert>}
      {cargando && <p role="status">Validando credenciales...</p>}
      <FormularioLogin onSubmit={ingresar} errorGeneral={errorGeneral} />
      <p className="mt-3 text-center">¿No tienes cuenta? <Link to="/registro">Regístrate aquí</Link></p>
    </div>
  );
}
