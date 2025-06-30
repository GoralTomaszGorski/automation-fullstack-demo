import React, { useState } from 'react';
import { login } from '../api/reqres';

export default function LoginForm({ onLoginSuccess }) {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');

  async function handleSubmit(e) {
    e.preventDefault();
    setError('');
    const res = await login(email, password);
    if (res.ok) {
      const data = await res.json();
      onLoginSuccess(data.token);
    } else {
      setError('Błąd logowania');
    }
  }

  return (
    <form onSubmit={handleSubmit}>
      <input
        type="email"
        placeholder="Email"
        value={email}
        onChange={e => setEmail(e.target.value)}
      />
      <input
        type="password"
        placeholder="Hasło"
        value={password}
        onChange={e => setPassword(e.target.value)}
      />
      <button type="submit">Zaloguj się</button>
      {error && <p style={{ color: 'red' }}>{error}</p>}
    </form>
  );
}
