<script setup>
import { computed } from 'vue';
import { useRoute } from 'vue-router';
import { useAuthStore } from '@/stores/authStore';

const route = useRoute();
const authStore = useAuthStore();

const activeMenu = computed(() => route.path);
const isAdmin = computed(() => authStore.isAdmin);
const isUser = computed(() => authStore.isLoggedIn && !authStore.isAdmin);
</script>

<template>
  <aside class="app-sidebar">
    <el-menu
        :default-active="activeMenu"
        class="el-menu-vertical-demo"
        router
    >
      <el-menu-item index="/">
        <span>메인 페이지</span>
      </el-menu-item>

      <!-- 일반 USER에게만 "내 정보" 노출 -->
      <el-menu-item v-if="isUser" index="/me">
        <span>내 정보</span>
      </el-menu-item>

      <!-- ADMIN 에게만 "모든 회원 조회" 노출 -->
      <el-menu-item v-if="isAdmin" index="/admin/users">
        <span>모든 회원 조회</span>
      </el-menu-item>
    </el-menu>
  </aside>
</template>

<style scoped>
.app-sidebar {
  width: 220px;
  border-right: 1px solid #eee;
  height: calc(100vh - 56px);
  overflow-y: auto;
}
</style>

