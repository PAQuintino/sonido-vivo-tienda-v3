// Cliente HTTP centralizado para consumir el backend Spring Boot.
export const API_URL = import.meta.env.VITE_API_URL || "http://localhost:8080/api";

export async function apiRequest(path, options = {}) {
  const token = localStorage.getItem("sv_token");
  const headers = { "Content-Type": "application/json", ...(options.headers || {}) };
  if (token) headers.Authorization = `Bearer ${token}`;
  const response = await fetch(`${API_URL}${path}`, { ...options, headers });
  if (response.status === 204) return null;
  const body = await response.json().catch(() => ({}));
  if (!response.ok) throw new Error(body.mensaje || body.message || `Error HTTP ${response.status}`);
  return body;
}

export const api = {
  login: (datos) => apiRequest("/auth/login", { method: "POST", body: JSON.stringify(datos) }),
  registro: (datos) => apiRequest("/auth/registro", { method: "POST", body: JSON.stringify(datos) }),
  productos: () => apiRequest("/productos"),
  categorias: () => apiRequest("/categorias"),
};
