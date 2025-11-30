<script setup>
import {useCourses} from "@/composables/useCourses.js";
import CourseListItem from "@/components/courses/CourseListItem.vue";
import {useCourseStore} from "@/stores/courseStore.js";

const { loading, error, searchKeyword, levelFilter, filteredCourses } = useCourses();

const courseStore = useCourseStore();

const handleToggleFavorite = (id) => {
  courseStore.toggleFavorite(id);
  courseStore.setLastViewedCourse(id);  // 마지막으로 즐겨찾기 버튼을 누른 강의를 마지막으로 본 강의로 처리
}
</script>

<template>
  <section>
    <h2>강의 목록 (리팩토링 후: Composable + Pinia)</h2>
    <div>
      <input v-model="searchKeyword" type="text" placeholder="강의명 또는 강사 검색" />
      <select v-model="levelFilter">
        <option value="all">전체 난이도</option>
        <option value="beginner">입문</option>
        <option value="intermediate">중급</option>
        <option value="advanced">고급</option>
      </select>
    </div>

    <p>선호 난이도 : {{ courseStore.preferredLevel }}</p>

    <p v-if="loading">로딩 중...</p>
    <p v-else-if="error">{{ error }}</p>

    <CourseListItem
        v-for="course in filteredCourses"
        :key="course.id"
        :course="course"
        :isFavorite="courseStore.isFavorite(course.id)"
        @toggle-favorite="() => handleToggleFavorite(course.id)"
    />
  </section>
</template>

<style></style>
