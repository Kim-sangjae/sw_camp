import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import { jwtDecode } from 'jwt-decode';

import { loginApi, refreshApi, logoutApi } from '@/api/authApi';
import router from '@/router';

/*
  useAuthStore: 인증 전용 전역 상태 모듈
  - accessToken: 현재 로그인한 사용자의 Access Token
  - user: { username, role, ... } 형태의 사용자 정보
  - loading: 로그인/재발급 등 진행 여부
*/
export const useAuthStore = defineStore('auth', () => {
    // 1) state
    const accessToken = ref(null);   // 문자열 or null
    const user = ref(null);          // { username, role, ... } or null
    const loading = ref(false);

    // 2) getters (computed 로 정의)
    /*
      isLoggedIn:
      - accessToken 과 user 둘 다 존재할 때 "로그인 된 상태"로 본다.
    */
    const isLoggedIn = computed(
        () => !!accessToken.value && !!user.value
    );

    /*
      isAdmin:
      - user.role 이 'ADMIN' 또는 'ROLE_ADMIN' 이면 관리자라고 판단한다.
      - 백엔드에서 role 을 어떤 문자열로 주는지에 따라 분기 처리.
    */
    const isAdmin = computed(() => {
        const role = user.value?.role;
        return role === 'ADMIN' || role === 'ROLE_ADMIN';
    });

    /*
      username:
      - user 가 없으면 빈 문자열, 있으면 user.username 반환
    */
    const username = computed(
        () => user.value?.username || ''
    );

    // 3) 내부 유틸 함수들
    /*
      setAccessToken:
      - accessToken ref 를 갱신하고
      - localStorage 에도 함께 저장/삭제한다.
    */
    const setAccessToken = (token) => {
        accessToken.value = token;
        if (token) {
            localStorage.setItem('accessToken', token);
        } else {
            localStorage.removeItem('accessToken');
        }
    };

    /*
      setUser:
      - user ref 를 갱신하고
      - localStorage 에도 함께 저장/삭제한다.
    */
    const setUser = (userData) => {
        user.value = userData || null;
        if (userData) {
            localStorage.setItem('user', JSON.stringify(userData));
        } else {
            localStorage.removeItem('user');
        }
    };

    /*
      setUserFromToken:
      - accessToken 문자열에서 JWT payload 를 파싱하여 username, role 을 추출해서 user 에 세팅한다.
      - jwt-decode 라이브러리 사용
        const payload = jwtDecode(token);
        payload.sub, payload.role 같은 식으로 값 접근.
    */
    const setUserFromToken = (token) => {
        try {
            const payload = jwtDecode(token);
            const usernameFromToken = payload.sub || payload.username;
            const roleFromToken = payload.role;
            if (!usernameFromToken || !roleFromToken) {
                setUser(null);
                return;
            }
            setUser({
                username: usernameFromToken,
                role: roleFromToken,
            });
        } catch (e) {
            console.error('Failed to decode JWT', e);
            setUser(null);
        }
    };

    /*
      loadFromStorage:
      - 새로고침(F5) 후에도 로그인 상태를 유지하기 위해 localStorage 에 저장해 둔 값을 읽어와 ref 에 복원한다.
    */
    const loadFromStorage = () => {
        const token = localStorage.getItem('accessToken');
        const userStr = localStorage.getItem('user');

        if (token) {
            accessToken.value = token;
        }
        if (userStr) {
            try {
                user.value = JSON.parse(userStr);
            } catch {
                user.value = null;
            }
        }
    };

    /*
      clearAuthState:
      - refresh 토큰 재발급 실패 등 네트워크 요청 없이 프런트 쪽 인증 상태만 비우고 싶을 때 사용하는 함수.
      - accessToken, user, 에러/로딩 상태를 초기화하고, 로그인 페이지로 이동한다.
    */
    const clearAuthState = () => {
        setAccessToken(null);
        setUser(null);
        loading.value = false;
        router.push('/login');
    };

    // 4) actions (실제 동작 로직)
    /*
      login:
      - /api/v1/auth/login 을 호출해서 accessToken 을 받는다.
      - 성공 시:
          1) setAccessToken 로 토큰 저장
          2) setUserFromToken 으로 JWT payload 기반 user 세팅
      - 실패 시:
          errorMessage 를 반환하여 화면에서 표시할 수 있게 한다.
    */
    const login = async ({ username, password }) => {
        loading.value = true;

        try {
            const res = await loginApi(username, password)
            const { success, data, message } = res.data;
            if(!success) {
                return {
                    success: false,
                    message: message || '로그인에 실패했습니다.'
                }
            }

            setAccessToken(data.accessToken)
            setUserFromToken(data.accessToken)

            return { success : true }
        } catch(e) {
            return {
                success: false,
                message: e.response?.data?.message || '로그인 신청 중 오류가 발생했습니다.'
            }
        } finally {
            loading.value = false;
        }
    };

    /*
      refreshTokens:
      - /api/v1/auth/refresh 를 호출해서 새 accessToken 을 받는다.
      - HttpOnly 쿠키에 있는 refreshToken 을 서버가 읽어서 처리하는 구조.
      - 성공 시: accessToken 및 user (payload 기반) 갱신.
      - 실패 시: accessToken, user 를 비우고 에러를 던진다.
        (실제 clearAuthState 호출은 인터셉터에서 수행)
    */
    const refreshTokens = async () => {

        try {
            const res = await refreshApi()
            const { success, data, message } = res.data;
            if(!success) {
                throw new Error(message || '토큰 재발급 실패')
            }
            setAccessToken(data.accessToken)
            setUserFromToken(data.accessToken)
        } catch(e) {
            setAccessToken(null)
            setUser(null)
            throw e;
        }
    };

    /*
      logout:
      - /api/v1/auth/logout 을 호출해서 서버 측에서 refreshToken 을 무효화한다.
      - 서버 요청 성공/실패와 상관 없이 클라이언트의 accessToken, user, 에러/로딩 상태를 모두 초기화하고 로그인 페이지로 이동한다.
    */
    const logout = async () => {
        // TODO: logoutApi 호출 및 상태 초기화, router 이동 로직 작성
        try {
            await logoutApi();
        } catch(e) {
            // 서버 에러여도 클라이언트 상태는 정리
        } finally {
            setAccessToken(null);
            setUser(null);
            loading.value = false;
            router.replace('/login');
        }


    };

    // 5) 외부로 공개할 state / getters / actions 반환
    return {
        accessToken,
        user,
        loading,
        isLoggedIn,
        isAdmin,
        username,
        setAccessToken,
        setUser,
        setUserFromToken,
        loadFromStorage,
        clearAuthState,
        login,
        refreshTokens,
        logout,
    };
});