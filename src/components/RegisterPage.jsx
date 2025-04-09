import urlConfig from '@/config/urlConfig';
import axios from 'axios';
import { useRouter } from 'next/navigation';
import React, { useState } from 'react';

export const RegisterPage = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [fullName, setFullName] = useState('');
  const [username, setUsername] = useState('');
  const [error, setError] = useState(null);
  const [statusCode, setStatusCode] = useState(200);
  const router = useRouter();

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const requestData = {
        email: email,
        password: password,
        fullName: fullName,
        username: username,
      };

      console.log('requestData:', requestData);
      console.log(
        `register url - ${urlConfig.baseUrl}${urlConfig.registerurl}`,
      );

      const response = await axios.post(
        `${urlConfig.baseUrl}${urlConfig.registerurl}`,
        requestData,
        {
          headers: {
            'Content-Type': 'application/json',
            'X-API-VERSION': 1,
          },
        },
      );
      console.log('response:', response);
      const responseCode = response?.data.code;

      if (responseCode === 200) {
        router.push('/login'); // Redirect to login page
      }
    } catch (error) {
      console.log('error:', error);
      setStatusCode(error.response?.status);
      setError(error.response?.data || error.message);
    }
  };

  return (
    <div className="flex flex-col gap-5 items-center p-6">
      <div className="w-5/12 lg:w-4/12">
        <h1 className="text-2xl font-bold mb-4">Register</h1>
        <input
          type="text"
          placeholder="email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          className="w-full p-2 border rounded-lg mb-4"
        />

        <input
          type="text"
          placeholder="full name"
          value={fullName}
          onChange={(e) => setFullName(e.target.value)}
          className="w-full p-2 border rounded-lg mb-4"
        />
        <input
          type="username"
          placeholder="username"
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
            Error: {error.message || 'Please try again later.'}
          </div>
        )}
        <button
          className="px-4 py-2 bg-blue-300 text-white rounded-md hover:bg-amber-700 cursor-pointer transition ease-in duration-300"
          onClick={handleLogin}
          type="submit"
        >
          Register
        </button>
      </div>
    </div>
  );
};

export default RegisterPage;
