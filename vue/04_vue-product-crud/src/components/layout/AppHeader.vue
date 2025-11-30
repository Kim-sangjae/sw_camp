<template>
  <header class="app-header">
    <div class="app-header-inner">
      <h1 class="logo" @click="goHome">
        Product Admin
      </h1>

      <nav class="nav">
        <RouterLink
            :to="{ name: 'product-list' }"
            :class="['nav-link', { active: isActive('/products') && !route.path.includes('/new') }]"
        >
          상품 목록
        </RouterLink>
        <RouterLink
            :to="{ name: 'product-create' }"
            :class="['nav-link', { active: route.path.startsWith('/products/new') }]"
        >
          상품 등록
        </RouterLink>
      </nav>
    </div>
  </header>
</template>

<script setup>
import { useRouter, useRoute, RouterLink } from 'vue-router';

const router = useRouter();
const route = useRoute();

const goHome = () => {
  router.push('/');
};

const isActive = (prefix) => {
  return route.path.startsWith(prefix);
};
</script>

<style scoped>
.app-header {
  border-bottom: 1px solid #eee;
  padding: 12px 24px;
  background-color: #ffffff;
}

.app-header-inner {
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.logo {
  margin: 0;
  cursor: pointer;
  font-size: 20px;
  font-weight: 600;
}

.nav {
  display: flex;
  gap: 8px;
}

.nav-link {
  padding: 8px 16px;
  border-radius: 20px;
  text-decoration: none;
  font-size: 14px;
  color: #555;
  border: 1px solid transparent;
  transition: all 0.15s ease-in-out;
}

.nav-link:hover {
  border-color: #dcdfe6;
  background-color: #f5f7fa;
}

.nav-link.active {
  border-color: #409eff;
  background-color: #ecf5ff;
  color: #409eff;
  font-weight: 500;
}
</style>
