'use client';

import axios from 'axios';
import React, { useState } from 'react';
import urlConfig from '@/config/urlConfig';
import { useRouter } from 'next/navigation';
import Input from './ui/Input';
import Button from './ui/Button';

export default function Login({ setToken }) {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState(null);
  const [isLoading, setIsLoading] = useState(false);
  const router = useRouter();

  const handleLogin = async (e) => {
    e.preventDefault();
    setIsLoading(true);
    setError(null);

    try {
      const response = await axios.post(
        `${urlConfig.baseUrl}${urlConfig.authUrl}`,
        { email, password },
        {
          headers: {
            'Content-Type': 'application/json',
            'X-API-VERSION': 1,
          },
        },
      );
      const token = response?.data.data.token;
      setToken(token);
    } catch (error) {
      setError(error.response?.data || error.message);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-gray-50 dark:bg-gray-900 transition-colors duration-300">
      <div className="flex flex-col items-center p-6">
        <div className="w-full max-w-md space-y-6">
          <h1 className="text-2xl font-bold text-center text-gray-900 dark:text-white">
            Login
          </h1>
          
          <form onSubmit={handleLogin} className="space-y-4">
            <Input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              placeholder="Email address"
              disabled={isLoading}
            />
            
            <Input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              placeholder="Password"
              disabled={isLoading}
            />

            {error && (
              <div className="text-red-500 dark:text-red-400 text-sm">
                {error.message || 'Invalid credentials'}
              </div>
            )}

            <div className="flex justify-between items-center gap-4">
              <Button
                type="submit"
                isLoading={isLoading}
                disabled={!email || !password}
              >
                Login
              </Button>

              <Button
                variant="secondary"
                onClick={() => router.push('/register')}
                disabled={isLoading}
              >
                Register
              </Button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}
