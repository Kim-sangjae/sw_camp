<script setup>

import {useRoute, useRouter} from "vue-router";

const route = useRoute();
const router = useRouter();

const login = () => {
  // 여기서는 단순히 토큰이 있다고 가정
  localStorage.setItem('ACCESS_TOKEN', 'dummy-token');

  // mypage로 이동 (또는 이전에 redirect 쿼리를 보유하고 있다면 해당 페이지로 이동)
  const redirect = route.query.redirect || '/mypage'
  router.replace(redirect); // push로 작성하면 history에 남고 replace로 작성하면 history X
}
</script>

<template>
  <div>
    <h1>Login</h1>
    <p>
      실제 서비스에서는 여기서 API를 호출 해 JWT 토큰 등을 발급 받는다.
    </p>
    <button @click="login">로그인</button>
  </div>
</template>

<style scoped>

</style>