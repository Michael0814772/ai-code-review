import axios from 'axios';
import urlConfig from '@/config/urlConfig';

class CodeReviewService {
  constructor(baseUrl = urlConfig.baseUrl, version = urlConfig.version) {
    this.baseUrl = baseUrl;
    this.version = version;
  }

  async reviewCode(code, token) {
    const requestData = {
      requestId: this.generateRequestId(),
      messages: [{ content: code }],
    };

    const response = await axios.post(
      `${this.baseUrl}${urlConfig.generateUrl}`,
      requestData,
      {
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
          'X-API-VERSION': this.version,
        },
      }
    );

    return response.data.data;
  }

  generateRequestId() {
    return Math.random().toString(36).substring(2, 15);
  }
}

export default new CodeReviewService();
