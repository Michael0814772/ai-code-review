'use-client';
import axios from 'axios';
import React, { useState } from 'react';
import urlConfig from '@/config/urlConfig';
import { useRouter } from 'next/navigation';

export default function Login({ setToken }) {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState(null);
  const [statusCode, setStatusCode] = useState(200);
  const router = useRouter();

  // console.log(`baseUrl: ${urlConfig.baseUrl}, authUrl: ${urlConfig.authUrl}`);

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const requestData = {
        email: email,
        password: password,
      };

      console.log(`url for login: ${urlConfig.baseUrl}${urlConfig.authUrl}`);

      const response = await axios.post(
        `${urlConfig.baseUrl}${urlConfig.authUrl}`, // Send the request to the authUrl
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

  const handleRegister = async (e) => {
    router.push('/register'); // Redirect to register page
    return;
  };

  return (
    <div className="flex flex-col gap-5 items-center p-6">
      <div className="w-5/12 lg:w-4/12">
        <h1 className="text-2xl font-bold mb-4">Login</h1>
        <input
          type="text"
          placeholder="Username/email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
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
        <div className="flex justify-between items-center ">
          <button
            className="px-4 py-2 bg-blue-300 text-white rounded-md hover:bg-amber-700 cursor-pointer transition ease-in duration-300"
            onClick={handleLogin}
            type="submit"
          >
            Login
          </button>

          <p>OR</p>

          <button
            className="px-4 py-2 cursor-pointer"
            onClick={handleRegister}
            type="submit"
          >
            Register
          </button>
        </div>
      </div>
    </div>
  );
}

// export default Login;
