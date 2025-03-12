'use client';

import axios from 'axios';
import { useState } from 'react';

export default function HomePage() {
  const [code, setCode] = useState('');
  const [review, setReview] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(false);

  const handleReview = async ({ token }) => {
    setLoading(true);
    try {
      const requestData = {
        requestId: '4455576778777',
        messages: [
          {
            content: code,
          },
        ],
      };
      console.log('token in homepage:', token);

      const response = await axios.post(
        'http://localhost:8085/ai-code-review/generate',
        requestData,
        {
          headers: {
            'Content-Type': 'application/json',
            Authorization:
              'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzZWd1bjIzMiIsImlhdCI6MTc0MTU5ODI5NSwiZXhwIjoxNzQxNjA0Mjk1fQ.OVBXKj0Hkw5dIo1-1Cb7921-7X1KqaYyehu2o59wlJc',
            'X-API-VERSION': 1,
          },
        },
      );
      setReview(response.data.data);
      console.log('Review:', response.data.data);
      console.log('Review:', review);
    } catch (error) {
      console.error(
        'Error reviewing code:',
        error.response?.data || error.message,
      );
      setError(true);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="flex flex-col items-center p-6">
      <h1 className="text-2xl font-bold mb-4">AI Code Review</h1>
      <textarea
        className="w-full p-2 border rounded-lg mb-4"
        rows="6"
        placeholder="Paste your code here..."
        value={code}
        onChange={(e) => setCode(e.target.value)}
      />
      <button
        className="px-4 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600"
        onClick={handleReview}
        disabled={loading}
      >
        {loading ? 'Reviewing...' : 'Review Code'}
      </button>
      {error ? (
        <div className="mt-6 w-full max-w-2xl p-4 bg-red-100 border border-red-400 rounded-lg">
          <h2 className="text-xl font-semibold text-red-600">Error</h2>
          <p className="mt-2 text-red-700">
            {error.message || 'Something went wrong.'}
          </p>
        </div>
      ) : (
        review && (
          <div className="mt-6 w-full max-w-2xl">
            <h2 className="text-xl font-semibold">
              Review Score: {review.score}/100
            </h2>
            <ul className="mt-4 space-y-4">
              {review?.recommendations?.length > 0 ? (
                <ul className="mt-4 space-y-4">
                  {review.recommendations.map((rec, index) => (
                    <li key={index} className="p-4 border rounded-lg">
                      <p className="font-semibold">{rec}</p>
                    </li>
                  ))}
                </ul>
              ) : (
                <p className="mt-4 text-gray-500">
                  No recommendations available.
                </p>
              )}
            </ul>
          </div>
        )
      )}
    </div>
  );
}

// export default HomePage
