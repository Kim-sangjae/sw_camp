<script setup>
const props = defineProps({
  course: {
    type: Object,
    required: true
  },
  isFavorite: {
    type: Boolean,
    default: false
  }
});

const emit = defineEmits(['toggle-favorite']);

const onToggleFavorite = () => {
  emit('toggle-favorite', props.course.id);
};
</script>

<template>
  <article class="course-item">
    <h3>{{ course.title }}</h3>
    <p>
      {{ course.instructor }} · {{ course.level }} · {{ course.duration }}시간
    </p>
    <button type="button" @click="onToggleFavorite">
      {{ isFavorite ? '즐겨찾기 해제' : '즐겨찾기' }}
    </button>
  </article>
</template>

<style>
.course-item {
  border: 1px solid #ddd;
  padding: 12px;
  margin-bottom: 8px;
  border-radius: 4px;
}
</style>