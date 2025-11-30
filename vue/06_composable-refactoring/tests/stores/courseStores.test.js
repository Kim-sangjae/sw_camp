// Pinia store에 대한 단위 테스트 예제
// - setActivePinia / createPinia: 테스트용 Pinia 인스턴스 생성
// - useCourseStore: 우리가 만든 store를 실제로 호출하여 상태/액션 검증

import { setActivePinia, createPinia } from 'pinia';
import { useCourseStore } from '@/stores/courseStore';

describe('courseStore', () => {
    // beforeEach: 각 테스트 실행 전마다 공통 준비 작업 수행
    beforeEach(() => {
        // 테스트 간 상태가 섞이지 않도록 매번 fresh 인스턴스를 생성
        setActivePinia(createPinia());
    });

    it('초기 상태가 올바르게 설정되어야 한다.', () => {
        const store = useCourseStore();

        // 초기 favoriteIds는 빈 배열
        expect(store.favoriteIds).toEqual([]);
        // 초기 선호 난이도는 beginner
        expect(store.preferredLevel).toBe('beginner');
        // 초기 lastViewedCourseId는 null
        expect(store.lastViewedCourseId).toBeNull();
        // isFavorite getter는 함수여야 한다.
        expect(typeof store.isFavorite).toBe('function');
    });

    it('toggleFavorite는 즐겨찾기 ID를 추가/제거해야 한다.', () => {
        const store = useCourseStore();

        // 처음엔 비어 있음
        expect(store.favoriteIds).toEqual([]);

        // 1 추가
        store.toggleFavorite(1);
        expect(store.favoriteIds).toEqual([1]);
        expect(store.isFavorite(1)).toBe(true);

        // 다시 1 토글 시 제거
        store.toggleFavorite(1);
        expect(store.favoriteIds).toEqual([]);
        expect(store.isFavorite(1)).toBe(false);
    });

    it('setPreferredLevel은 선호 난이도를 변경해야 한다.', () => {
        const store = useCourseStore();

        expect(store.preferredLevel).toBe('beginner');

        store.setPreferredLevel('intermediate');
        expect(store.preferredLevel).toBe('intermediate');

        store.setPreferredLevel('advanced');
        expect(store.preferredLevel).toBe('advanced');
    });

    it('setLastViewedCourse는 마지막 본 강의 ID를 저장해야 한다.', () => {
        const store = useCourseStore();

        expect(store.lastViewedCourseId).toBeNull();

        store.setLastViewedCourse(10);
        expect(store.lastViewedCourseId).toBe(10);

        store.setLastViewedCourse(99);
        expect(store.lastViewedCourseId).toBe(99);
    });
});
