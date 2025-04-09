'use client';

export default function Input({
  type = 'text',
  value,
  onChange,
  placeholder,
  className = '',
  isTextArea = false,
  rows = 6,
  disabled = false,
}) {
  const baseStyles = `w-full p-4 border-2 border-gray-200 rounded-lg 
                    focus:ring-2 focus:ring-blue-500 focus:border-transparent
                    transition-all duration-300 ease-in-out
                    text-sm sm:text-base
                    shadow-sm hover:shadow-md
                    bg-white dark:bg-gray-800 dark:border-gray-700
                    disabled:opacity-50 disabled:cursor-not-allowed`;

  if (isTextArea) {
    return (
      <textarea
        value={value}
        onChange={onChange}
        placeholder={placeholder}
        rows={rows}
        disabled={disabled}
        className={`${baseStyles} resize-y min-h-[200px] ${className}`}
      />
    );
  }

  return (
    <input
      type={type}
      value={value}
      onChange={onChange}
      placeholder={placeholder}
      disabled={disabled}
      className={`${baseStyles} h-12 ${className}`}
    />
  );
}
