<script setup>
  import { computed, onMounted, ref } from 'vue';
  import { fetchCourses } from '@/api/courseApi';
  import CourseListItem from '@/components/courses/CourseListItem.vue';

  const courses = ref([]);
  const loading = ref(false);
  const error = ref(null);

  const searchKeyword = ref('');
  const levelFilter = ref('all');
  const localFavoriteIds = ref([]);

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

  const toggleFavorite = (id) => {
    if (localFavoriteIds.value.includes(id)) {
      localFavoriteIds.value = localFavoriteIds.value.filter((cid) => cid !== id);
      return;
    }
    localFavoriteIds.value.push(id);
  };

  onMounted(loadCourses);
</script>

<template>
  <section>
    <h2>강의 목록 (리팩토링 전)</h2>

    <div>
      <input v-model="searchKeyword" type="text" placeholder="강의명 또는 강사 검색" />
      <select v-model="levelFilter">
        <option value="all">전체 난이도</option>
        <option value="beginner">입문</option>
        <option value="intermediate">중급</option>
        <option value="advanced">고급</option>
      </select>
    </div>

    <p v-if="loading">로딩 중...</p>
    <p v-else-if="error">{{ error }}</p>

    <CourseListItem
      v-for="course in filteredCourses"
      :key="course.id"
      :course="course"
      :isFavorite="localFavoriteIds.includes(course.id)"
      @toggle-favorite="toggleFavorite"
    />
  </section>
</template>

<style></style>
