import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';

// Importa os estilos do PrimeReact
import 'primereact/resources/themes/saga-blue/theme.css';  // Tema (pode escolher outro)
import 'primereact/resources/primereact.min.css';           // CSS principal do PrimeReact
import 'primeicons/primeicons.css';                         // Ícones do PrimeReact
import 'primeflex/primeflex.css';                           // Utilitário de layout (opcional)

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
