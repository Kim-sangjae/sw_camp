<script setup>
import { RouterLink } from 'vue-router';
</script>

<template>
  <header class="app-header">
    <nav class="nav">
      <RouterLink to="/">Home</RouterLink>
      <RouterLink to="/courses-before">강의 목록 (리팩토링 전)</RouterLink>
      <RouterLink to="/courses-after">강의 목록 (리팩토링 후)</RouterLink>
      <RouterLink to="/preferences">내 취향 (Pinia)</RouterLink>
    </nav>
  </header>
</template>

<style>
.app-header {
  padding: 12px 16px;
  border-bottom: 1px solid #ddd;
}
.nav {
  display: flex;
  gap: 12px;
}
.nav a.router-link-active {
  font-weight: bold;
  text-decoration: underline;
}
</style>