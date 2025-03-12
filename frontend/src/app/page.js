'use client';

import { useState } from 'react';
import HomePage from './components/HomePage';
import Login from './components/LoginPage';

export default function Home() {
  const [token, setToken] = useState(null);
  return (
    <div className="">
      {!token ? <Login setToken={setToken} /> : <HomePage token={token} />}
    </div>
  );
}
