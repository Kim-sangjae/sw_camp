import axios from 'axios';

// TODO: axios 인스턴스를 생성하고
// 1) baseURL 에 import.meta.env.VITE_API_BASE_URL 사용
// 2) 공통 응답 인터셉터에서 ApiResponse(success, data, errorCode, message) 구조 처리
// 3) 에러(HTTP 4xx, 5xx) 시 적절히 throw

const api = axios.create({
    baseURL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080',
    withCredentials: false // 쿠키 X, Stateless REST API 호출 시 설정
});

api.interceptors.response.use(
  (response) => {
      const body = response.data;
      if(body && body.success === false) { // 성공이지만 success=false인 경우 에러 처리
          const error = new Error(body.message || '요청이 실패했습니다.');
          error.response = response;
          throw error;
      }
      return response;
  },
  (error) => {
    // HTTP 에러 처리
      return Promise.reject(error);
  }
);

export default api;
