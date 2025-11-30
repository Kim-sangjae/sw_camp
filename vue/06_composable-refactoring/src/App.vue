<script setup>
import AppHeader from './components/layout/AppHeader.vue';
</script>

<template>
  <div class="app">
    <AppHeader />
    <main class="app-main">
      <RouterView />
    </main>
  </div>
</template>

<style>
.app {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}
.app-main {
  flex: 1;
  padding: 16px;
}
</style>
