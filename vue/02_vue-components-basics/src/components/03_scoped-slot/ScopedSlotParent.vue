<script setup lang="ts">

import ScopedSlotChild from "@/components/03_scoped-slot/ScopedSlotChild.vue";
</script>

<template>
  <div class="container">
    <h2>03. Scoped Slot 테스트</h2>
    <ScopedSlotChild>
      <!--
        # : v-slot의 축약형
        #default : 자식에서 slot에 name을 별도로 지정하지 않았을 경우의 기본 값
        { user } : 구조 분해 할당 문법으로 slotProps.user로 자식이 전달한 user에 접근한다.
      -->
      <template #default="{ user }">
        <strong>{{ user.name }}</strong> - <em>{{ user.age }}세</em>
      </template>
    </ScopedSlotChild>
  </div>
</template>

<style scoped>

</style>