'use client';

export default function Button({ 
  children, 
  onClick, 
  type = 'button',
  disabled = false,
  variant = 'primary',
  isLoading = false,
  fullWidth = false,
  className = '',
}) {
  const baseStyles = 'px-6 py-3 font-semibold rounded-lg transform transition-all duration-300 ease-in-out flex items-center justify-center gap-2 disabled:opacity-50 disabled:cursor-not-allowed disabled:hover:scale-100 shadow-md hover:shadow-lg';
  
  const variants = {
    primary: 'bg-gradient-to-r from-blue-500 to-blue-600 hover:from-blue-600 hover:to-blue-700 text-white hover:scale-105 active:scale-95',
    secondary: 'bg-gray-100 hover:bg-gray-200 text-gray-800 hover:scale-102 active:scale-98',
    danger: 'bg-gradient-to-r from-red-500 to-red-600 hover:from-red-600 hover:to-red-700 text-white hover:scale-105 active:scale-95',
  };

  const widthClass = fullWidth ? 'w-full' : 'w-auto';
  const loadingClass = isLoading ? 'animate-pulse' : '';

  return (
    <button
      type={type}
      onClick={onClick}
      disabled={disabled || isLoading}
      className={`${baseStyles} ${variants[variant]} ${widthClass} ${loadingClass} ${className}`}
    >
      {isLoading && (
        <svg className="animate-spin h-5 w-5 mr-2" viewBox="0 0 24 24">
          <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4" fill="none" />
          <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z" />
        </svg>
      )}
      {children}
    </button>
  );
}
