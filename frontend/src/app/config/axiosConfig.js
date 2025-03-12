axios.interceptors.request.use((config) => {
  console.log('📤 Request:', {
    method: config.method.toUpperCase(),
    url: config.url,
    headers: config.headers,
    data: config.data,
  });

  return config;
});

axios.interceptors.response.use(
  (response) => {
    console.log('✅ Response:', {
      status: response.status,
      url: response.config.url,
      headers: response.headers,
      data: response.data,
    });
    return response;
  },
  (error) => {
    console.error('❌ Response Error:', {
      status: error.response?.status,
      url: error.config?.url,
      data: error.response?.data,
    });
    return Promise.reject(error);
  },
);
