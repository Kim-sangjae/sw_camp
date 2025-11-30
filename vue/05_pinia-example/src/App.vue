<script setup>

import {useCounterStore} from "@/stores/counter.js";
/* Pinia Counter Store에서 외부로 노출 한 state, getters, actions를 가져온다. */
const counterStore = useCounterStore();
</script>

<template>
  <RouterView />

<!--
<h1>Pinia Store 테스트</h1>
  <p>현재 값 : {{ counterStore.count }}</p>
  <p>두 배의 값 : {{ counterStore.doubleCount }}</p>
  <button @click="counterStore.increment">카운트 증가</button>
  -->
</template>

<style scoped></style>
