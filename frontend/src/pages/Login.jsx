import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "../styles/style.css";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState(null);
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    setMessage(null);

    try {
      const response = await fetch("http://localhost:3000/api/v1/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password }),
      });

      if (response.ok) {
        const data = await response.json();
        localStorage.setItem("token", data.token);
        setMessage("Zalogowano pomyślnie.");
        navigate("/dashboard");
      } else {
        const errorData = await response.json();
        setMessage(errorData.message || "Błąd logowania.");
      }
    } catch (error) {
      setMessage("Błąd połączenia z serwerem.");
    }
  };

  return (
    <div className="container">
      <h1>Logowanie</h1>
      <form onSubmit={handleLogin}>
        <input
          type="email"
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
        /><br />
        <input
          type="password"
          placeholder="Hasło"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        /><br />
        <button type="submit">Zaloguj</button>
      </form>
      {message && <p>{message}</p>}
      <p>
        Nie masz konta? <a href="/register">Zarejestruj się</a>
      </p>
    </div>
  );
};

export default Login;
