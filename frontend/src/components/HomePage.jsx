'use client';

import axios from 'axios';
import { useRouter } from 'next/navigation';
import { useState } from 'react';

export default function HomePage({ token }) {
  const [code, setCode] = useState('');
  const [review, setReview] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(false);
  const router = useRouter();

  const baseUrl = process.env.BASE_PATH_API_REVIEW_URL;
  const generateUrl = process.env.CODE_REVIEW_PATH_API_REVIEW_URL;
  const version = process.env.API_VERSION;

  const handleReview = async () => {
    if (!token) {
      router.push('/'); // Redirect if no token
      return;
    }

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
      // console.log('token in homepage:', token);

      const response = await axios.post(
        `${baseUrl}${generateUrl}`,
        requestData,
        {
          headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`,
            'X-API-VERSION': version,
          },
        },
      );
      setReview(response.data.data);
      // console.log('Review:', response.data.data);
      // console.log('Review:', review);
    } catch (error) {
      if (error.response?.data.code === 401) {
        router.push('/'); // Redirect to login on 401
      }
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
        type="submit"
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
