// import { setActivePinia, createPinia } from 'pinia';
// import { useAuthStore } from '@/stores/authStore';
import { createRouter, createMemoryHistory } from 'vue-router';

import HomeView from '@/views/HomeView.vue';
import PreferencesView from '@/views/PreferencesView.vue';

// 실제 앱에서는 Pinia store(useAuthStore)를 사용하지만
// 여기서는 테스트를 단순화하기 위해 로그인 여부를 나타내는 플래그 변수만 사용한다.
let isLoggedInFlag = false;

function createAuthTestRouter() {
    const router = createRouter({
        history: createMemoryHistory(),
        routes: [
            { path: '/', name: 'home', component: HomeView },
            {
                path: '/preferences',
                name: 'preferences',
                component: PreferencesView,
                meta: { requiresAuth: true }
            }
        ]
    });

    router.beforeEach((to) => {
        // 실제 코드라면:
        // const authStore = useAuthStore();
        // if (to.meta.requiresAuth && !authStore.isLoggedIn) { ... }

        // 여기서는 isLoggedInFlag 값을 통해 인증 여부를 판단한다.
        if (to.meta.requiresAuth && !isLoggedInFlag) {
            return { name: 'home' };
        }
    });

    return router;
}

describe('Router Guard: /preferences 접근 제어', () => {
    // 실제 Pinia store를 쓸 경우에는 매 테스트마다 새로운 Pinia 인스턴스를 활성화한다.
    // beforeEach(() => {
    //     setActivePinia(createPinia());
    // });

    it('로그인되지 않았을 때 /preferences 접근 시 /로 리다이렉트되어야 한다.', async () => {
        const router = createAuthTestRouter();

        // 로그인되지 않은 상태로 둔다.
        isLoggedInFlag = false;

        await router.push('/preferences');
        await router.isReady();

        expect(router.currentRoute.value.fullPath).toBe('/');
    });

    it('로그인된 상태라면 /preferences 접근이 허용되어야 한다.', async () => {
        const router = createAuthTestRouter();

        // 로그인된 상태로 설정
        isLoggedInFlag = true;

        await router.push('/preferences');
        await router.isReady();

        expect(router.currentRoute.value.fullPath).toBe('/preferences');
    });
});
