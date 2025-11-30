import { createRouter, createMemoryHistory } from 'vue-router';

import HomeView from '@/views/HomeView.vue';
import CoursesBeforeView from '@/views/CoursesBeforeView.vue';
import CoursesAfterView from '@/views/CoursesAfterView.vue';
import PreferencesView from '@/views/PreferencesView.vue';

export function createTestRouter() {
    return createRouter({
        history: createMemoryHistory(),
        routes: [
            { path: '/', name: 'home', component: HomeView },
            {
                path: '/courses-before',
                name: 'courses-before',
                component: CoursesBeforeView
            },
            {
                path: '/courses-after',
                name: 'courses-after',
                component: CoursesAfterView
            },
            {
                path: '/preferences',
                name: 'preferences',
                component: PreferencesView
            }
        ]
    });
}