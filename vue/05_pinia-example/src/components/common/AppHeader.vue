<script setup>
import { computed } from 'vue';
import { useRouter } from 'vue-router';
import { useAuthStore } from '@/stores/authStore';

const router = useRouter();
const authStore = useAuthStore();

const isLoggedIn = computed(() => authStore.isLoggedIn);
const isUser = computed(() => authStore.isLoggedIn && !authStore.isAdmin);
const username = computed(() => authStore.username);
const roleLabel = computed(() => (authStore.isAdmin ? 'ADMIN' : 'USER'));

const goHome = () => {
  router.push({ name: 'home' });
};

const goLogin = () => {
  router.push({ name: 'login' });
};

const goRegister = () => {
  router.push({ name: 'register' });
};

const goMyInfo = () => {
  router.push({ name: 'my-info' });
};

const handleLogout = async () => {
  await authStore.logout();
};
</script>

<template>
  <header class="app-header">
    <div class="left">
      <span class="logo" @click="goHome">My Security App</span>
    </div>
    <div class="right">
      <template v-if="isLoggedIn">
        <el-dropdown>
          <span class="el-dropdown-link">
            {{ username }} ({{ roleLabel }})
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <!-- 일반 USER에게만 "내 정보" 노출 -->
              <el-dropdown-item v-if="isUser" @click="goMyInfo">
                내 정보
              </el-dropdown-item>
              <el-dropdown-item divided @click="handleLogout">
                로그아웃
              </el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </template>
      <template v-else>
        <el-button type="primary" @click="goLogin">로그인</el-button>
        <el-button @click="goRegister">회원가입</el-button>
      </template>
    </div>
  </header>
</template>

<style scoped>
.app-header {
  height: 56px;
  padding: 0 16px;
  border-bottom: 1px solid #eee;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.logo {
  font-weight: 600;
  cursor: pointer;
}

.right {
  display: flex;
  align-items: center;
  gap: 8px;
}
</style>
