import {computed, onMounted, ref} from "vue";
import {fetchCourses} from "@/api/courseApi.js";

export const useCourses = () => {

    const courses = ref([]);
    const loading = ref(false);
    const error = ref(null);

    const searchKeyword = ref('');
    const levelFilter = ref('all');

    const filteredCourses = computed(() => {
        let list = courses.value;

        if (searchKeyword.value) {
            const keyword = searchKeyword.value.toLowerCase();
            list = list.filter(
                (course) =>
                    course.title.toLowerCase().includes(keyword) ||
                    course.instructor.toLowerCase().includes(keyword)
            );
        }

        if (levelFilter.value !== 'all') {
            list = list.filter((course) => course.level === levelFilter.value);
        }

        return list;
    });

    const loadCourses = async () => {
        loading.value = true;
        error.value = null;
        try {
            courses.value = await fetchCourses();
        } catch (e) {
            error.value = '강의 목록을 불러오지 못했습니다.';
        } finally {
            loading.value = false;
        }
    };
    onMounted(loadCourses);

    return {
        courses,
        loading,
        error,
        searchKeyword,
        levelFilter,
        filteredCourses,
        loadCourses
    };
};
