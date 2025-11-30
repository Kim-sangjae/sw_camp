<script setup>

import {useCourseStore} from "@/stores/courseStore.js";
import {computed} from "vue";

const courseStore = useCourseStore();

const preferredLevel = computed({
  get: () => courseStore.preferredLevel,
  set: (value) => courseStore.setPreferredLevel(value)
});

const handlePreferredLevelChange = (event) => {
  preferredLevel.value = event.target.value;
}
</script>

<template>
  <section>
    <h2>내 강의 취향 설정 (Pinia)</h2>
    <div>
      <label>선호 난이도:</label>
      <select :value="preferredLevel" @change="handlePreferredLevelChange">
        <option value="all">전체 난이도</option>
        <option value="beginner">입문</option>
        <option value="intermediate">중급</option>
        <option value="advanced">고급</option>
      </select>
    </div>

    <div style="margin-top: 16px">
      <h3>즐겨찾기 강의 ID</h3>
      <p>{{ courseStore.favoriteIds.join(', ') || '없음'}}</p>
    </div>

    <div style="margin-top: 16px">
      <h3>마지막으로 본 강의 ID</h3>
      <p>{{ courseStore.lastViewedCourseId ?? '없음'}}</p>
    </div>


  </section>
</template>

<style></style>
