<script setup>

import {useRoute, useRouter} from "vue-router";

//현재 라우트 객체 (path, name, params, query, meta 등)
const currentRoute = useRoute();
// 코드로 라우트 이동이 필요할 때 사용하는 라우터 인스턴스
const router = useRouter();

const changeRouter = () => {
  router.push('/pathvariable/2');
}
</script>

<template>
<div>
  <h1>HOME</h1>
  <h2>현재 라우트 정보</h2>
  <p>path : {{ currentRoute.path }}</p>
  <p>name : {{ currentRoute.name }}</p>
<!--  <p>{{ currentRoute }}</p>-->
  <button @click="changeRouter">/pathvariable/2로 이동</button>
</div>
</template>

<style scoped>

</style>