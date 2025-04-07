const urlConfig = {
  baseUrl:
    process.env.NEXT_PUBLIC_BASE_PATH_API_REVIEW_URL || 'http://localhost:8085',
  generateUrl:
    process.env.NEXT_PUBLIC_CODE_REVIEW_PATH_API_REVIEW_URL ||
    '/ai-code-review/generate',
  version: process.env.NEXT_PUBLIC_API_VERSION || 1,
  authUrl:
    process.env.NEXT_PUBLIC_CODE_REVIEW_PATH_API_AUTH_LOGIN_USER_URL ||
    '/ai-code-review/auth/login-user',
  registerurl:
    process.env.NEXT_PUBLIC_CODE_REVIEW_PATH_API_AUTH_REGISTER_USER_URL ||
    '/ai-code-review/auth/register-userr',
};

export default urlConfig;
