// Composable(useCourses)에 대한 테스트 예제
// - fetchCourses를 mock 처리하여 API 의존성을 제거
// - 검색/난이도 필터 로직이 정상 동작하는지 검증
// - wrapper.vm에서는 ref가 언래핑된 값으로 보이기 때문에 .value가 아니라 바로 wrapper.vm.xxx 형태로 접근

import { defineComponent } from 'vue';
// mount: 테스트용으로 컴포넌트를 렌더링
// flushPromises: 비동기 Promise들이 모두 처리될 때까지 기다림
import { mount, flushPromises } from '@vue/test-utils';
import { useCourses } from '@/composables/useCourses';

// vi.mock: 특정 모듈을 가짜 구현으로 대체하여 테스트 시 외부 의존성 제거
vi.mock('@/api/courseApi', () => {
    return {
        // fetchCourses를 가짜 함수로 구현 (항상 동일한 배열 반환)
        fetchCourses: vi.fn(async () => {
            return [
                {
                    id: 1,
                    title: 'Vue 입문',
                    instructor: '홍길동',
                    level: 'beginner',
                    duration: 10
                },
                {
                    id: 2,
                    title: 'Vue 심화',
                    instructor: '유관순',
                    level: 'advanced',
                    duration: 20
                },
                {
                    id: 3,
                    title: 'Spring 입문',
                    instructor: '신사임당',
                    level: 'beginner',
                    duration: 15
                }
            ];
        })
    };
});

// 테스트 전용 호스트 컴포넌트
// - setup에서 useCourses() 호출 후 그대로 반환
// - template에서는 filteredCourses.length만 간단히 표시
const createUseCoursesHost = () => {
    return defineComponent({
        name: 'UseCoursesHost',
        setup: () => {
            // Composition API: setup 안에서 useCourses 호출
            return useCourses();
        },
        template: `
      <div>
        <span data-test="count">{{ filteredCourses.length }}</span>
      </div>
    `
    });
};

// describe: useCourses 관련 테스트들을 하나의 그룹으로 묶음
describe('useCourses', () => {
    // 초기 로딩이 끝난 뒤 상태가 올바른지 검증
    it('초기 로딩 이후 courses와 filteredCourses가 올바르게 채워져야 한다.', async () => {
        const Host = createUseCoursesHost();
        const wrapper = mount(Host);

        // onMounted에서 호출된 비동기 loadCourses가 모두 끝날 때까지 대기
        await flushPromises();
        // 반응형 상태 갱신이 DOM에 반영될 때까지 한 번 더 대기
        await wrapper.vm.$nextTick();

        // wrapper.vm: setup에서 반환한 값(ref 포함)이 언래핑된 상태로 노출됨
        const { courses, filteredCourses, loading, error } = wrapper.vm;

        expect(loading).toBe(false);
        expect(error).toBeNull();
        expect(courses).toHaveLength(3);
        expect(filteredCourses).toHaveLength(3);
    });

    // 검색어(searchKeyword)에 따른 필터 로직 테스트
    it('searchKeyword로 강의 제목/강사 검색 필터가 적용되어야 한다.', async () => {
        const Host = createUseCoursesHost();
        const wrapper = mount(Host);

        await flushPromises();
        await wrapper.vm.$nextTick();

        // ref지만 wrapper.vm에서는 언래핑되므로 .value 없이 바로 할당/접근
        // "Vue" 검색 시 Vue 관련 2개만 남아야 함
        wrapper.vm.searchKeyword = 'Vue';
        await flushPromises();
        await wrapper.vm.$nextTick();
        expect(wrapper.vm.filteredCourses.map((c) => c.title)).toEqual([
            'Vue 입문',
            'Vue 심화'
        ]);

        // "신사임당" 검색 시 Spring 입문 1개만 남아야 함
        wrapper.vm.searchKeyword = '신사임당';
        await flushPromises();
        await wrapper.vm.$nextTick();
        expect(wrapper.vm.filteredCourses.map((c) => c.title)).toEqual(['Spring 입문']);
    });

    // 난이도(levelFilter)에 따른 필터 로직 테스트
    it('levelFilter로 난이도 필터가 적용되어야 한다.', async () => {
        const Host = createUseCoursesHost();
        const wrapper = mount(Host);

        await flushPromises();
        await wrapper.vm.$nextTick();

        // 기본값: levelFilter = 'all' → 전체 3개
        expect(wrapper.vm.filteredCourses).toHaveLength(3);

        // beginner만 필터링
        wrapper.vm.levelFilter = 'beginner';
        await flushPromises();
        await wrapper.vm.$nextTick();
        expect(wrapper.vm.filteredCourses.map((c) => c.level)).toEqual([
            'beginner',
            'beginner'
        ]);

        // advanced만 필터링
        wrapper.vm.levelFilter = 'advanced';
        await flushPromises();
        await wrapper.vm.$nextTick();
        expect(wrapper.vm.filteredCourses.map((c) => c.level)).toEqual(['advanced']);
    });
});