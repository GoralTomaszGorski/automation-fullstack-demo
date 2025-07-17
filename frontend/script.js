// script.js

const API_BASE = 'https://reqres.in/api';
const API_KEY = 'reqres-free-v1';
document.addEventListener('DOMContentLoaded', () => {
  const loginForm = document.getElementById('loginForm');
  const registerForm = document.getElementById('registerForm');

  const token = localStorage.getItem('token');

  // Sprawdzenie tylko na stronie dashboard
  if (window.location.pathname.includes('dashboard.html') && !token) {
    alert('Zaloguj się najpierw!');
    window.location.href = 'index.html';
  }

  // LOGIN
  if (loginForm) {
    loginForm.addEventListener('submit', async (e) => {
      e.preventDefault();
      const email = loginForm.email.value;
      const password = loginForm.password.value;

      try {
        const response = await fetch(`${API_BASE}/login`, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'x-api-key': API_KEY
          },
          body: JSON.stringify({ email, password })
        });

        const result = await response.json();

        if (response.ok && result.token) {
          localStorage.setItem('token', result.token);
          window.location.href = 'dashboard.html';
        } else {
          document.getElementById('loginMessage').textContent = result.error || 'Nieprawidłowe dane logowania.';
        }
      } catch (err) {
        document.getElementById('loginMessage').textContent = 'Błąd połączenia z API.';
      }
    });
  }

  // REGISTRATION
  if (registerForm) {
    registerForm.addEventListener('submit', async (e) => {
      e.preventDefault();
      const email = registerForm.email.value;
      const password = registerForm.password.value;

      try {
        const response = await fetch(`${API_BASE}/register`, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'x-api-key': API_KEY
          },
          body: JSON.stringify({ email, password })
        });

        const result = await response.json();

        if (response.ok && result.token) {
          localStorage.setItem('token', result.token);
          document.getElementById('registerMessage').textContent = 'Zarejestrowano pomyślnie!';
          window.location.href = 'dashboard.html';
        } else {
          document.getElementById('registerMessage').textContent = result.error || 'Błąd rejestracji.';
        }
      } catch (err) {
        document.getElementById('registerMessage').textContent = 'Błąd połączenia z API.';
      }
    });
  }
});

const addUserForm = document.getElementById('addUserForm');
const addUserMessage = document.getElementById('addUserMessage');

addUserForm.addEventListener('submit', async (e) => {
  e.preventDefault();

  const first_name = document.getElementById('firstName').value.trim();
  const last_name = document.getElementById('lastName').value.trim();
  const email = document.getElementById('email').value.trim();

  if (!first_name || !last_name || !email) {
    addUserMessage.style.color = 'red';
    addUserMessage.textContent = 'Wypełnij wszystkie pola.';
    return;
  }

  try {
    const response = await fetch(`${API_BASE}/users`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'x-api-key': API_KEY
      },
      body: JSON.stringify({ first_name, last_name, email })
    });

    if (response.ok) {
      addUserMessage.style.color = 'green';
      addUserMessage.textContent = 'Użytkownik dodany (symulacja).';
      addUserForm.reset();
      fetchUsers();  // Odśwież listę
    } else {
      addUserMessage.style.color = 'red';
      addUserMessage.textContent = 'Błąd dodawania użytkownika.';
    }
  } catch (err) {
    addUserMessage.style.color = 'red';
    addUserMessage.textContent = 'Błąd połączenia.';
  }
});

});
