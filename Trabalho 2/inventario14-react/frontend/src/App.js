import React, { useState } from "react";
import Login from "./components/Login";
import InventoryList from "./components/InventoryList";
import { login as loginService } from "./services/authService";
import { Button } from 'primereact/button';

function App() {
  const [auth, setAuth] = useState(null); // Guarda {username, password, role}
  const [showLogin, setShowLogin] = useState(false);

  const handleLogin = async (username, password) => {
    try {
      const user = await loginService(username, password);
      setAuth(user);
      setShowLogin(false);
    } catch (err) {
      alert("Credenciais inválidas.");
    }
  };

  const handleLogout = () => {
    setAuth(null);
  };

  return (
    <div className="App">
      <header className="p-d-flex p-jc-between p-ai-center p-mb-4 p-px-4" style={{ backgroundColor: '#1976d2', color: 'white', padding: '1rem' }}>
        <h1>Inventário</h1>
        <div>
          {auth ? (
            <>
              <span style={{ marginRight: '1rem' }}>Olá, {auth.username} ({auth.role})</span>
              <Button label="Logout" onClick={handleLogout} className="p-button-secondary" />
            </>
          ) : (
            <Button label="Login" onClick={() => setShowLogin(true)} />
          )}
        </div>
      </header>
      <div className="p-mx-4">
        <InventoryList auth={auth} />
      </div>
      {showLogin && <Login onLogin={handleLogin} />}
    </div>
  );
}

export default App;
