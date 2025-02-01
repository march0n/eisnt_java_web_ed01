export function login(username, password) {
    // Apenas para demonstração: se username for "admin" ou "user", o login é considerado bem-sucedido.
    if ((username === "admin" && password === "admin") || (username === "user" && password === "user")) {
      const role = username === "admin" ? "ADMIN" : "USER";
      return Promise.resolve({ username, password, role });
    }
    return Promise.reject("Credenciais inválidas");
  }
  