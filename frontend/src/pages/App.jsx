// src/App.jsx
import React from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";

import Login from "./pages/Login";
import Register from "./pages/Register";
import Dashboard from "./pages/Dashboard";
import AddUser from "./pages/AddUser";

const App = () => {
  // Prosta funkcja do sprawdzenia, czy użytkownik jest zalogowany (token w localStorage)
  const isAuthenticated = () => !!localStorage.getItem("token");

  return (
    <Router>
      <Routes>
        <Route
          path="/"
          element={isAuthenticated() ? <Navigate to="/dashboard" /> : <Login />}
        />
        <Route
          path="/login"
          element={isAuthenticated() ? <Navigate to="/dashboard" /> : <Login />}
        />
        <Route
          path="/register"
          element={isAuthenticated() ? <Navigate to="/dashboard" /> : <Register />}
        />
        <Route
          path="/dashboard"
          element={isAuthenticated() ? <Dashboard /> : <Navigate to="/login" />}
        />
        <Route
          path="/add-user"
          element={isAuthenticated() ? <AddUser /> : <Navigate to="/login" />}
        />
        {/* Inne trasy, np. 404 */}
        <Route path="*" element={<h2>Strona nie znaleziona (404)</h2>} />
      </Routes>
    </Router>
  );
};

export default App;
