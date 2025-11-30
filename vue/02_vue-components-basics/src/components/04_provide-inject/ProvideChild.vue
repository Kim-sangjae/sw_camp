<script setup>
/* inject : 조상 컴포넌트가 provide한 값을 꺼내 사용 */
import {inject} from "vue";

const injectedCount = inject('count');
const injectedIncrease = inject('increaseCount');
</script>

<template>
<h3>ProvideChild</h3>
  <p>주입 받은 count : {{ injectedCount }}</p>
  <button type="button" @click="injectedIncrease()">
    injectedIncrease 호출
  </button>
</template>

<style scoped>

</style>