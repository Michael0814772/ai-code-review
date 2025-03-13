'use-client';
import axios from 'axios';
import React, { useState } from 'react';

export default function Login({ setToken }) {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState(null);
  const [statusCode, setStatusCode] = useState(200);

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const requestData = {
        email: username,
        password: password,
      };

      const response = await axios.post(
        'http://localhost:8085/ai-code-review/auth/login-user',
        requestData,
        {
          headers: {
            'Content-Type': 'application/json',
            'X-API-VERSION': 1,
          },
        },
      );
      const token = response?.data.data.token;
      setToken(token); // Pass the token to the parent component
    } catch (error) {
      setStatusCode(error.response?.status);
      setError(error.response?.data || error.message);
    }
  };

  return (
    <div className="flex flex-col gap-5 items-center p-6">
      <div className="w-5/12">
        <h1 className="text-2xl font-bold mb-4">Login</h1>
        <input
          type="text"
          placeholder="Username/email"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          className="w-full p-2 border rounded-lg mb-4"
        />
        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          className="w-full p-2 border rounded-lg mb-4"
        />
        {error && (
          <div className="text-red-500 mb-4">
            Error: {error.message || 'User not found.'}
          </div>
        )}
        <button
          className="px-4 py-2 bg-blue-300 text-white rounded-md hover:bg-amber-700 cursor-pointer transition ease-in duration-300"
          onClick={handleLogin}
          type="submit"
        >
          Login
        </button>
      </div>
    </div>
  );
}

// export default Login;
