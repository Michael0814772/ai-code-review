'use client';

import { ThemeProvider } from '@/context/ThemeContext';
import ThemeToggle from '../ui/ThemeToggle';

export default function MainLayout({ children }) {
  return (
    <ThemeProvider>
      <div className="min-h-screen bg-gray-50 dark:bg-gray-900 transition-colors duration-300">
        <ThemeToggle />
        {children}
      </div>
    </ThemeProvider>
  );
}
