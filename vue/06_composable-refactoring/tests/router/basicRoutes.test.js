import { mount } from '@vue/test-utils';
import { createPinia, setActivePinia } from 'pinia';
import { createTestRouter } from './testRouter';
import App from '@/App.vue';

describe('Router: 기본 페이지 이동', () => {

    let pinia;
    let router;
    let wrapper;

    // 각 테스트 실행 전마다 초기 상태를 깨끗하게 준비
    beforeEach(async () => {
        // Pinia를 테스트용으로 새로 생성
        pinia = createPinia();
        setActivePinia(pinia);

        // Router도 메모리 기반으로 새로 생성
        router = createTestRouter();

        // App.vue를 Router와 함께 mount
        // 하면 실제 화면처럼 RouterView가 동작함
        wrapper = mount(App, {
            global: {
                plugins: [router, pinia]
            }
        });
    });

    it('/ 경로에서 HomeView가 렌더링되어야 한다.', async () => {
        // "/"(홈 화면)으로 이동
        await router.push('/');
        await router.isReady();

        // HomeView 내부에 있는 문구가 실제로 보이는지 확인
        expect(wrapper.text()).toContain('Composable 구조화');
    });

    it('/courses-before 경로에서 CoursesBeforeView가 렌더링되어야 한다.', async () => {
        // "/courses-before" 경로로 이동
        await router.push('/courses-before');
        await router.isReady();

        // 리팩토링 전 화면 문구가 표시되는지 확인
        expect(wrapper.text()).toContain('강의 목록 (리팩토링 전)');
    });

    it('/courses-after 경로에서 CoursesAfterView가 렌더링되어야 한다.', async () => {
        // "/courses-after" 경로로 이동
        await router.push('/courses-after');
        await router.isReady();

        // 리팩토링 후 화면 문구가 표시되는지 확인
        expect(wrapper.text()).toContain('강의 목록 (리팩토링 후: Composable + Pinia)');
    });

    it('/preferences 경로에서 PreferencesView가 렌더링되어야 한다.', async () => {
        // "/preferences" 경로로 이동
        await router.push('/preferences');
        await router.isReady();

        // 선호도 설정 화면 문구가 표시되는지 확인
        expect(wrapper.text()).toContain('내 강의 취향 설정 (Pinia)');
    });
});
