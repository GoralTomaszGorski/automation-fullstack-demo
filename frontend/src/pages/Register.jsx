import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "../styles/style.css";

const Register = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [name, setName] = useState("");
  const [message, setMessage] = useState(null);
  const navigate = useNavigate();

  const handleRegister = async (e) => {
    e.preventDefault();
    setMessage(null);

    try {
      const response = await fetch("http://localhost:3000/api/v1/auth/register", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ name, email, password }),
      });

      if (response.ok) {
        setMessage("Rejestracja zakończona sukcesem!");
        navigate("/login");
      } else {
        const errorData = await response.json();
        setMessage(errorData.message || "Błąd rejestracji.");
      }
    } catch (error) {
      setMessage("Błąd połączenia z serwerem.");
    }
  };

  return (
    <div className="container">
      <h1>Rejestracja</h1>
      <form onSubmit={handleRegister}>
        <input
          type="text"
          placeholder="Imię"
          value={name}
          onChange={(e) => setName(e.target.value)}
          required
        /><br />
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
        <button type="submit">Zarejestruj</button>
      </form>
      {message && <p>{message}</p>}
      <p>
        Masz już konto? <a href="/login">Zaloguj się</a>
      </p>
    </div>
  );
};

export default Register;
