// 프리젠테이션 컴포넌트(CourseListItem)에 대한 테스트 예제
// - 전달된 props가 화면에 제대로 표시되는지
// - 버튼 클릭 시 toggle-favorite 이벤트가 발생하는지 검증

// mount: Vue 컴포넌트를 테스트용 DOM에 렌더링하는 함수
import { mount } from '@vue/test-utils';
import CourseListItem from '@/components/courses/CourseListItem.vue';

// describe: 관련된 테스트들을 하나의 그룹으로 묶는 함수
describe('CourseListItem', () => {

    // createWrapper: 매번 같은 props를 반복하지 않기 위한 헬퍼 함수
    // overrides는 기본 props를 원하는 값으로 덮어쓸 때 사용
    const createWrapper = (overrides = {}) => {
        const defaultProps = {
            course: {
                id: 1,
                title: 'Vue.js 입문',
                instructor: '홍길동',
                level: 'beginner',
                duration: 12
            },
            isFavorite: false
        };

        return mount(CourseListItem, {
            props: {
                ...defaultProps,     // 기본 props
                ...overrides         // 테스트에서 변경한 props
            }
        });
    };

    // it: 실제 테스트 하나를 정의하는 함수
    it('강의 정보가 화면에 표시되어야 한다.', () => {
        const wrapper = createWrapper();

        // expect: 결과를 검증하는 함수 ("이 값이 맞아야 한다")
        expect(wrapper.text()).toContain('Vue.js 입문');
        expect(wrapper.text()).toContain('홍길동');
        expect(wrapper.text()).toContain('beginner');
        expect(wrapper.text()).toContain('12시간');
    });

    it('isFavorite=false일 때 버튼 라벨은 "즐겨찾기"여야 한다.', () => {
        const wrapper = createWrapper({ isFavorite: false });

        // wrapper.get: 특정 DOM 요소를 찾아 반환
        const button = wrapper.get('button');
        expect(button.text()).toBe('즐겨찾기');
    });

    it('isFavorite=true일 때 버튼 라벨은 "즐겨찾기 해제"여야 한다.', () => {
        const wrapper = createWrapper({ isFavorite: true });

        const button = wrapper.get('button');
        expect(button.text()).toBe('즐겨찾기 해제');
    });

    it('버튼 클릭 시 toggle-favorite 이벤트가 발생해야 한다.', async () => {
        const wrapper = createWrapper();

        const button = wrapper.get('button');

        // trigger: 사용자가 클릭한 것처럼 DOM 이벤트를 실행
        await button.trigger('click');

        // emitted: 컴포넌트에서 emit된 이벤트 목록 조회
        const emitted = wrapper.emitted('toggle-favorite');

        expect(emitted).toBeTruthy();       // 이벤트가 1번이라도 발생했는가?
        expect(emitted[0][0]).toBe(1);      // 첫 호출의 payload가 course.id 인가?
    });
});
