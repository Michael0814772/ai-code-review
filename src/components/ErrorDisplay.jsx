'use client';

export default function ErrorDisplay({ error }) {
  if (!error) return null;

  return (
    <div className="mt-6 w-full max-w-4xl mx-auto px-4 sm:px-6 lg:px-8">
      <div 
        className="p-4 bg-red-50 border-l-4 border-red-500 rounded-lg
                   shadow-md hover:shadow-lg
                   transform transition-all duration-300 ease-in-out
                   animate-fadeIn"
      >
        <div className="flex items-center">
          <div className="flex-shrink-0">
            <svg className="h-5 w-5 text-red-500" viewBox="0 0 20 20" fill="currentColor">
              <path fillRule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zM8.707 7.293a1 1 0 00-1.414 1.414L8.586 10l-1.293 1.293a1 1 0 101.414 1.414L10 11.414l1.293 1.293a1 1 0 001.414-1.414L11.414 10l1.293-1.293a1 1 0 00-1.414-1.414L10 8.586 8.707 7.293z" clipRule="evenodd" />
            </svg>
          </div>
          <div className="ml-3">
            <h2 className="text-lg font-medium text-red-800">Error</h2>
            <p className="mt-1 text-sm text-red-700">
              {error.message || 'Something went wrong.'}
            </p>
          </div>
        </div>
      </div>
    </div>
  );
}
