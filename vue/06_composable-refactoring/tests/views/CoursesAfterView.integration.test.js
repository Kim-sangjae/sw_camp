import { mount } from '@vue/test-utils';
import { setActivePinia, createPinia } from 'pinia';
import CoursesAfterView from '@/views/CoursesAfterView.vue';
import { useCourseStore } from '@/stores/courseStore.js';

// useCourses를 실제 구현 대신 "간단한 더미 데이터"를 돌려주는 함수로 모킹한다.
// 이렇게 하면 네트워크/비동기 의존성 없이 화면 + store 흐름만 집중해서 테스트할 수 있다.
vi.mock('@/composables/useCourses.js', () => {
    return {
        useCourses: () => {
            return {
                // 로딩/에러는 항상 정상 상태라고 가정
                loading: false,
                error: null,
                // 검색/필터는 이 테스트에서는 사용하지 않으므로 기본값만 제공
                searchKeyword: '',
                levelFilter: 'all',
                // 화면에 렌더링될 강의 목록(2개)
                filteredCourses: [
                    {
                        id: 1,
                        title: '테스트 강의 1',
                        instructor: '홍길동',
                        level: 'beginner',
                        duration: 10
                    },
                    {
                        id: 2,
                        title: '테스트 강의 2',
                        instructor: '유관순',
                        level: 'advanced',
                        duration: 20
                    }
                ]
            };
        }
    };
});

describe('통합 테스트: CoursesAfterView + useCourseStore', () => {
    beforeEach(() => {
        // 매 테스트마다 새로운 Pinia 인스턴스를 활성화하여 상태 섞임 방지
        setActivePinia(createPinia());
    });

    it('첫 번째 강의의 즐겨찾기 버튼 클릭 시 store 상태가 변경되어야 한다.', async () => {
        // 리팩토링 후 화면 컴포넌트를 Pinia와 함께 마운트
        const wrapper = mount(CoursesAfterView, {
            global: {
                // CoursesAfterView 내부에서 useCourseStore()를 사용하므로
                // Pinia 플러그인을 global.plugins에 등록해준다.
                plugins: [createPinia()]
            }
        });

        // 현재 활성화된 store 인스턴스를 가져온다.
        const courseStore = useCourseStore();

        // 초기 상태는 비어 있다고 가정
        expect(courseStore.favoriteIds).toEqual([]);
        expect(courseStore.lastViewedCourseId).toBeNull();

        // 화면에 렌더링된 버튼들을 모두 찾는다.
        // CoursesAfterView -> CourseListItem 내부에 있는 버튼들이다.
        const buttons = wrapper.findAll('button');

        // 첫 번째 강의 카드의 "즐겨찾기" 버튼을 클릭했다고 가정
        await buttons[0].trigger('click');

        // CoursesAfterView의 handleToggleFavorite(id)가 호출되면,
        // - toggleFavorite(id)
        // - setLastViewedCourse(id)
        // 가 순서대로 실행되어야 한다.

        // favoriteIds에 1번 강의가 추가되었는지 확인
        expect(courseStore.favoriteIds).toEqual([1]);

        // lastViewedCourseId도 1로 설정되었는지 확인
        expect(courseStore.lastViewedCourseId).toBe(1);
    });
});
