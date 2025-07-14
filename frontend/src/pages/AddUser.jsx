import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "../styles/style.css";

const AddUser = () => {
  const [formData, setFormData] = useState({ name: "", email: "", password: "" });
  const [message, setMessage] = useState("");
  const navigate = useNavigate();

  const token = localStorage.getItem("token");

  const handleChange = (e) => {
    setFormData((prev) => ({
      ...prev,
      [e.target.name]: e.target.value,
    }));
  };

  const handleAddUser = async (e) => {
    e.preventDefault();

    try {
      const response = await fetch("http://localhost:3000/api/v1/register", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(formData),
      });

      if (response.status === 201 || response.ok) {
        setMessage("Użytkownik dodany pomyślnie!");
        setFormData({ name: "", email: "", password: "" });
      } else {
        const data = await response.json();
        setMessage(data.message || "Błąd podczas dodawania użytkownika.");
      }
    } catch (error) {
      setMessage("Błąd połączenia z serwerem.");
    }
  };

  const handleGoBack = () => {
    navigate("/dashboard");
  };

  return (
    <div className="container">
      <h1>Dodaj użytkownika</h1>
      <form onSubmit={handleAddUser}>
        <input
          type="text"
          name="name"
          placeholder="Imię i nazwisko"
          value={formData.name}
          onChange={handleChange}
          required
        />
        <input
          type="email"
          name="email"
          placeholder="Adres e-mail"
          value={formData.email}
          onChange={handleChange}
          required
        />
        <input
          type="password"
          name="password"
          placeholder="Hasło"
          value={formData.password}
          onChange={handleChange}
          required
        />
        <button type="submit">Dodaj</button>
      </form>
      {message && <p>{message}</p>}
      <button onClick={handleGoBack}>Powrót</button>
    </div>
  );
};

export default AddUser;
