<script setup>
import {provide, readonly, ref} from "vue";
import MiddleWrapper from "@/components/04_provide-inject/MiddleWrapper.vue";

const count = ref(1);
const increaseCount = () => {
  count.value++;
};
// 하위 컴포넌트에서 count라는 명칭으로 count 상태값을 읽기 전용으로 사용 가능
provide('count', readonly(count));
// 하위 컴포넌트에서 increaseCount 라는 명칭으로 상태 값 변경 함수 호출 가능
provide('increaseCount', increaseCount);

// 중간에 MiddleWrapper 같은 컴포넌트가 몇 단게 끼더라도 props로 전달할 필요 없이
// 자식 컴포넌트에서 inject로 바로 꺼내올 수 있다.
</script>

<template>
  <div class="container">
    <h2>04. provide / inject 예제</h2>
    <MiddleWrapper/>
  </div>
</template>

<style scoped>

</style>