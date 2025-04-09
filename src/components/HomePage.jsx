'use client';

import { useRouter } from 'next/navigation';
import { useState } from 'react';
import CodeReviewService from '@/services/codeReviewService';
import CodeInput from './CodeInput';
import ReviewDisplay from './ReviewDisplay';
import ErrorDisplay from './ErrorDisplay';

export default function HomePage({ token }) {
  const [code, setCode] = useState('');
  const [review, setReview] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const router = useRouter();

  const handleReview = async () => {
    if (!token) {
      router.push('/');
      return;
    }

    setLoading(true);
    setError(null);

    try {
      const reviewData = await CodeReviewService.reviewCode(code, token);
      setReview(reviewData);
    } catch (error) {
      if (error.response?.data.code === 401) {
        router.push('/');
      }
      setError(error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-gray-50 dark:bg-gray-900 transition-colors duration-300">
      <div className="flex flex-col items-center p-6">
        <h1 className="text-2xl font-bold mb-4 text-gray-900 dark:text-white">
          AI Code Review
        </h1>
        <CodeInput
          code={code}
          setCode={setCode}
          onSubmit={handleReview}
          isLoading={loading}
        />
        <ErrorDisplay error={error} />
        <ReviewDisplay review={review} />
      </div>
    </div>
  );
}
