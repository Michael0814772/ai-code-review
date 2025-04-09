'use client';

import Input from './ui/Input';
import Button from './ui/Button';

export default function CodeInput({ code, setCode, onSubmit, isLoading }) {
  return (
    <div className="w-full max-w-4xl mx-auto px-4 sm:px-6 lg:px-8">
      <div className="relative space-y-4">
        <Input
          isTextArea
          value={code}
          onChange={(e) => setCode(e.target.value)}
          placeholder="Paste your code here..."
        />
        
        <Button
          onClick={onSubmit}
          disabled={isLoading}
          type="submit"
          isLoading={isLoading}
          fullWidth
        >
          {isLoading ? 'Reviewing...' : 'Review Code'}
        </Button>
      </div>
    </div>
  );
}
