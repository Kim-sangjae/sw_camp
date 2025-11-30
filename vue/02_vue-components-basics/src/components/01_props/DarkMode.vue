<script setup>
/* defineEmits
* 부모가 @toggle="..." 로 받을 수 있는 이벤트를 선언하면
* 내부에서 emit('toggle')을 호출해 부모의 핸들러를 실행할 수 있다.
* */
  const emit = defineEmits(['toggle']);
  const onClick = () => emit('toggle');
</script>

<template>
  <button type="button" @click="onClick">
    다크모드 토글 버튼
  </button>
</template>

<style scoped>
button {
  margin : 8px 0;
}
</style>