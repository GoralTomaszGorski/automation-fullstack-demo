import React, { useState, useEffect } from 'react';
import LoginForm from './components/LoginForm';
import { fetchUsers } from './api/reqres';

export default function App() {
  const [token, setToken] = useState(null);
  const [users, setUsers] = useState([]);

  useEffect(() => {
    if (token) {
      fetchUsers(1).then(setUsers);
    }
  }, [token]);

  return (
    <div style={{ padding: 20 }}>
      {!token ? (
        <LoginForm onLoginSuccess={setToken} />
      ) : (
        <>
          <h2>Zalogowano! Token: {token}</h2>
          <h3>Użytkownicy:</h3>
          <ul>
            {users.map(u => (
              <li key={u.id}>
                {u.first_name} {u.last_name} ({u.email})
              </li>
            ))}
          </ul>
        </>
      )}
    </div>
  );
}
