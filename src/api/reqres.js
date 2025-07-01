export async function login(email, password) {
  const res = await fetch('https://reqres.in/api/login', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ email, password })
  });
  return res;
}

export async function fetchUsers(page = 1) {
  const res = await fetch(`https://reqres.in/api/users?page=${page}`);
  const json = await res.json();
  return json.data;
}
