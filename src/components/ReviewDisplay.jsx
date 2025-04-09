'use client';

export default function ReviewDisplay({ review }) {
  if (!review) return null;

  return (
    <div className="mt-6 w-full max-w-4xl mx-auto px-4 sm:px-6 lg:px-8">
      <div className="bg-white dark:bg-gray-800 rounded-lg shadow-md hover:shadow-lg
                    transform transition-all duration-300 ease-in-out
                    border border-gray-200 dark:border-gray-700
                    animate-fadeIn">
        <div className="p-6">
          <div className="flex items-center justify-between">
            <h2 className="text-xl font-semibold text-gray-900 dark:text-white">
              Review Score
            </h2>
            <span className="px-4 py-2 rounded-full text-sm font-semibold
                         bg-blue-100 text-blue-800 dark:bg-blue-900 dark:text-blue-200">
              {review.score}/100
            </span>
          </div>

          <div className="mt-6 space-y-4">
            {review?.recommendations?.length > 0 ? (
              <div className="space-y-4 animate-fadeInUp">
                {review.recommendations.map((rec, index) => (
                  <div
                    key={index}
                    className="p-4 bg-gray-50 dark:bg-gray-700 rounded-lg
                             border border-gray-200 dark:border-gray-600
                             transform transition-all duration-300 ease-in-out
                             hover:scale-[1.01] hover:shadow-md"
                  >
                    <p className="text-gray-700 dark:text-gray-300">{rec}</p>
                  </div>
                ))}
              </div>
            ) : (
              <p className="text-gray-500 dark:text-gray-400 text-center py-4">
                No recommendations available.
              </p>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}
