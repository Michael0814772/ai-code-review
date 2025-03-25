'use client';

import HomePage from '@/components/HomePage';
import Login from '@/components/LoginPage';
import { useState } from 'react';
import React from 'react';

const page = () => {
  const [token, setToken] = useState(null);
  // console.log('token:', token);
  return (
    <div className="flex min-h-screen flex-col overflow-hidden m-0 p-0">
      {!token ? <Login setToken={setToken} /> : <HomePage token={token} />}
    </div>
  );
};

export default page;
