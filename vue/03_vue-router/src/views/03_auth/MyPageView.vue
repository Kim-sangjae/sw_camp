<script setup>

import {useRouter} from "vue-router";

const router = useRouter();
const logout = () => {
  localStorage.removeItem('ACCESS_TOKEN');
  router.replace('/login');
};
</script>

<template>
  <div>
    <h1>MyPage</h1>
    <p>
      이 페이지는 meta.requiresAuth = true 인 설정이 있는 페이지이다.
    </p>
    <button @click="logout">로그아웃</button>
  </div>
</template>

<style scoped>

</style>