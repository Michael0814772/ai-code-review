'use client';

import LoginPage from '@/components/LoginPage';
import MainLayout from '@/components/layouts/MainLayout';
import React from 'react';

const page = () => {
  return (
    <MainLayout>
      <LoginPage />
    </MainLayout>
  );
};

export default page;
