import { defineStore } from 'pinia';
import {computed, ref} from "vue";

export const useCourseStore = defineStore('course', () => {
    // state
    const favoriteIds = ref([]);
    const preferredLevel = ref('beginner');
    const lastViewedCourseId = ref(null);

    // getter
    const isFavorite = computed(
        () => (id) => favoriteIds.value.includes(id)
    )

    // actions
    const toggleFavorite = (id) => {
        if(favoriteIds.value.includes(id)) {
            favoriteIds.value
                = favoriteIds.value.filter((cid) => cid !== id);
            return;
        }
        favoriteIds.value.push(id);
    }

    const setPreferredLevel = (level) => preferredLevel.value = level;

    const setLastViewedCourse = (id) => lastViewedCourseId.value = id;

    return {
        favoriteIds,
        preferredLevel,
        lastViewedCourseId,
        isFavorite,
        toggleFavorite,
        setPreferredLevel,
        setLastViewedCourse
    };
});