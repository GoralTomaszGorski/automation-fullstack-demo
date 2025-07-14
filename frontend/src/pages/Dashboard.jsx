import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "../styles/style.css";

const Dashboard = () => {
  const [users, setUsers] = useState([]);
  const [message, setMessage] = useState("");
  const navigate = useNavigate();

  const token = localStorage.getItem("token");

  useEffect(() => {
    if (!token) {
      navigate("/login");
      return;
    }

    const fetchUsers = async () => {
      try {
        const response = await fetch("http://localhost:3000/api/v1/users", {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });

        if (response.ok) {
          const data = await response.json();
          setUsers(data);
        } else {
          setMessage("Nie udało się pobrać użytkowników.");
        }
      } catch (error) {
        setMessage("Błąd połączenia z serwerem.");
      }
    };

    fetchUsers();
  }, [token, navigate]);

  const handleLogout = () => {
    localStorage.removeItem("token");
    navigate("/login");
  };

  return (
    <div className="container">
      <h1>Dashboard</h1>
      <button onClick={handleLogout}>Wyloguj</button>
      {message && <p>{message}</p>}
      {users.length > 0 ? (
        <ul>
          {users.map((user, index) => (
            <li key={index}>
              {user.name} – {user.email}
            </li>
          ))}
        </ul>
      ) : (
        !message && <p>Brak użytkowników do wyświetlenia.</p>
      )}
    </div>
  );
};

export default Dashboard;
