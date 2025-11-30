import { createRouter, createWebHistory } from 'vue-router';
import { useAuthStore } from '@/stores/authStore';

import MainLayout from '@/layouts/MainLayout.vue';
import HomeView from '@/views/HomeView.vue';
import LoginView from '@/views/auth/LoginView.vue';
import RegisterView from '@/views/auth/RegisterView.vue';
import MyInfoView from '@/views/user/MyInfoView.vue';
import UserListView from '@/views/admin/UserListView.vue';

const routes = [
    {
        path: '/',
        component: MainLayout,
        children: [
            {
                path: '',
                name: 'home',
                component: HomeView,
            },
            {
                path: 'me',
                name: 'my-info',
                component: MyInfoView,
                meta: { requiresAuth: true, requiresUser: true },
            },
            {
                path: 'admin/users',
                name: 'admin-users',
                component: UserListView,
                meta: { requiresAuth: true, requiresAdmin: true },
            },
        ],
    },
    {
        path: '/login',
        name: 'login',
        component: LoginView,
        meta: { guestOnly: true },
    },
    {
        path: '/register',
        name: 'register',
        component: RegisterView,
        meta: { guestOnly: true },
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

router.beforeEach((to, from) => {
    const authStore = useAuthStore();

    // 새로고침 대비
    if (!authStore.accessToken && !authStore.user) {
        authStore.loadFromStorage();
    }

    const isLoggedIn = authStore.isLoggedIn;
    const isAdmin = authStore.isAdmin;
    const isUser = isLoggedIn && !isAdmin;

    // TODO: guestOnly / requiresAuth / requiresAdmin / requiresUser 에 따른 라우터 가드 로직 작성

    // 이미 로그인 상태에서 guestOnly 접근 불가
    if(to.meta.guestOnly && isLoggedIn) {
        return { name : 'home' }
    }

    // 로그인 하지 않은 상태에서 requiresAuth 접근 불가
    if(to.meta.requiresAuth && !isLoggedIn) {
        return {
            name : 'login',
            query : { redirect : to.fullPath }
        }
    }

    // 관리자가 아닌 경우 requiresAdmin 접근 불가
    if(to.meta.requiresAdmin && !isAdmin) {
        return { name : 'home' }
    }

    // 일반 회원이 아닌 경우 requiresUser 접근 불가
    if(to.meta.requiresUser && !isUser) {
        return { name : 'home' }
    }

    // 그 외에는 그대로 진행
    return true;
});

export default router;