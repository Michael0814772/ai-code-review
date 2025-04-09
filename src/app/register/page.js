'use client';

import RegisterPage from '@/components/RegisterPage';
import MainLayout from '@/components/layouts/MainLayout';
import React from 'react';

const page = () => {
  return (
    <MainLayout>
      <RegisterPage />
    </MainLayout>
  );
};

export default page;
