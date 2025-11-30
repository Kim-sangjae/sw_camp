<script setup>
</script>

<template>
<div>
  <h1>Nested Layout</h1>
  <p>
    /nested 경로에 들어오면 항상 이 컴포넌트가 먼저 렌더링 되고
    아래 영역에 자식 라우트가 들어간다.
  </p>
  <!-- 중첩 된 RouterView -->
  <RouterView/>
</div>
</template>

<style scoped>

</style>