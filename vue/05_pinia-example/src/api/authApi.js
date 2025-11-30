import api from './http';

export function loginApi(username, password) {
    return api.post('/api/v1/auth/login', { username, password })
}

export function refreshApi() {
    return api.post('/api/v1/auth/refresh',
        {},
        { skipAuth : true } // request interceptor에게 "Authorization" 속성을 붙이지 않도록 알림
    );
}

export function logoutApi() {
    return api.post('/api/v1/auth/logout')
}

export function registerApi(payload) {
    return api.post('/api/v1/users', payload)
}