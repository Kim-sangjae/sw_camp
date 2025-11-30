<script setup>
import { computed } from 'vue';
import { useAuthStore } from '@/stores/authStore';

const authStore = useAuthStore();
const isLoggedIn = computed(() => authStore.isLoggedIn);
const username = computed(() => authStore.username);
const role = computed(() => authStore.user?.role || '');
</script>

<template>
  <div>
    <h2>메인 페이지</h2>
    <p v-if="!isLoggedIn">
      로그인하면 내 정보와 회원 목록을 확인할 수 있습니다.
    </p>
    <el-card v-else>
      <p>환영합니다, {{ username }} 님.</p>
    </el-card>
  </div>
</template>