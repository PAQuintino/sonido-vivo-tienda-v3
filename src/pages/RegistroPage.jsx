// El registro crea cuentas CLIENTE en la base de datos mediante la API REST.
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { FormularioRegistro } from "../components/organisms/FormularioRegistro";
import { api } from "../services/api";

export function RegistroPage() {
  const navigate = useNavigate();
  const [errorGeneral, setErrorGeneral] = useState("");
  const [cargando, setCargando] = useState(false);

  const registrar = async (datos) => {
    setCargando(true);
    try {
      await api.registro({ nombre: `${datos.nombre || ""} ${datos.apellidos || ""}`.trim(), correo: datos.correo, password: datos.password });
      navigate("/login", { state: { registroExitoso: true } });
    } catch (error) {
      setErrorGeneral(error.message || "No fue posible crear la cuenta. Verifica la conexión con el backend.");
    } finally {
      setCargando(false);
    }
  };

  return (
    <div className="mx-auto" style={{ maxWidth: "500px" }}>
      <h1 className="mb-1">Crear cuenta</h1>
      <p className="text-muted">Regístrate para comprar tus instrumentos favoritos.</p>
      {cargando && <p role="status">Creando cuenta...</p>}
      <FormularioRegistro onSubmit={registrar} errorGeneral={errorGeneral} />
      <p className="mt-3 text-center">¿Ya tienes cuenta? <Link to="/login">Inicia sesión</Link></p>
    </div>
  );
}
