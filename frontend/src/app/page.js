'use client';

import { useState } from 'react';
import HomePage from '../components/HomePage';
import Login from '../components/LoginPage';

export default function Home() {
  const [token, setToken] = useState(null);
  // console.log('token:', token);
  return (
    <div className="flex min-h-screen flex-col overflow-hidden m-0 p-0">
      {!token ? <Login setToken={setToken} /> : <HomePage token={token} />}
    </div>
  );
}
