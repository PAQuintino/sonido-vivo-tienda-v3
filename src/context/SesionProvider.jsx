// La sesión se autentica en Spring Security y se conserva mediante un JWT.
import { useState } from "react";
import { SesionContext } from "./sesionContext";
import { api } from "../services/api";

const CLAVE_USUARIO = "sv_usuario";
const CLAVE_TOKEN = "sv_token";

function leerUsuarioGuardado() {
  try { return JSON.parse(localStorage.getItem(CLAVE_USUARIO) || "null"); }
  catch { return null; }
}

export function SesionProvider({ children }) {
  const [usuario, setUsuario] = useState(leerUsuarioGuardado);

  const iniciarSesion = async (correo, password) => {
    try {
      const respuesta = await api.login({ correo, password });
      localStorage.setItem(CLAVE_TOKEN, respuesta.token);
      localStorage.setItem(CLAVE_USUARIO, JSON.stringify(respuesta.usuario));
      setUsuario(respuesta.usuario);
      return respuesta.usuario;
    } catch {
      return null;
    }
  };

  const cerrarSesion = () => {
    localStorage.removeItem(CLAVE_TOKEN);
    localStorage.removeItem(CLAVE_USUARIO);
    localStorage.removeItem("sv_sesion");
    setUsuario(null);
  };

  const refrescarUsuario = () => setUsuario(leerUsuarioGuardado());
  const valor = { usuario, iniciarSesion, cerrarSesion, refrescarUsuario };
  return <SesionContext.Provider value={valor}>{children}</SesionContext.Provider>;
}
