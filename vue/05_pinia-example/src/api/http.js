import axios from 'axios';
import { useAuthStore } from '@/stores/authStore';

const api = axios.create({
    baseURL: 'http://localhost:8080',
    withCredentials: true, // HttpOnly refreshToken 쿠키 전송
});

// Request 인터셉터: Authorization 헤더 자동 부착
api.interceptors.request.use(
    (config) => {
        // 해당 플래그가 있을 경우 header에 access token을 부착하지 않고 요청을 수행함
        if(config.skipAuth) return config;
        
        const authStore = useAuthStore();
        // TODO: accessToken 을 읽어서 Authorization 헤더에 Bearer 토큰으로 설정하는 로직 작성
        if(authStore.accessToken && !config.headers.Authorization) {
            config.headers.Authorization = `Bearer ${authStore.accessToken}`
        }
        return config;
    },
    (error) => Promise.reject(error),
);

let isRefreshing = false;

api.interceptors.response.use(
    (response) => response,
    async (error) => {
        const authStore = useAuthStore();
        const originalRequest = error.config;

        if (!error.response) {
            return Promise.reject(error);
        }

        const status = error.response.status;
        const url = originalRequest.url || '';

        // TODO: 401 응답 처리 및 토큰 재발급, 재시도, clearAuthState 호출 등 인터셉터 로직 작성

        // 401이 아닌 상황은 그대로 throw
        if(status !== 401) return Promise.reject(error);

        // auth 관련 엔드포인트의 401의 무한 루프 방지를 위해 그대로 throw
        if(url.includes('/api/v1/auth/')) return Promise.reject(error);

        // access token이 없는 상황도 그대로 throw
        if(!authStore.accessToken) return Promise.reject(error);

        // 이미 재시도한 요청이면 그대로 실패
        if(originalRequest._retry) return Promise.reject(error);

        // 일반 보호 API의 401 중 한 번만 refresh 시도
        if(isRefreshing) return Promise.reject(error);

        originalRequest._retry = true;
        isRefreshing = true;

        try {
            await authStore.refreshTokens();
            isRefreshing = false;

            // 401로 실패했던 기존 요청을 다시 수행
            originalRequest.headers.Authorization = `Bearer ${authStore.accessToken}`;
            return api(originalRequest);
        } catch(e) {
            isRefreshing = false;
            authStore.clearAuthState();
            return Promise.reject(e);
        }
    },
);

export default api;