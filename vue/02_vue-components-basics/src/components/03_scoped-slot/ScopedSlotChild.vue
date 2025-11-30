<script setup>

/* Scoped Slot에서 사용할 user 목록
* 해당 데이터를 slot을 통해 부모에게 노출한다. */
import {ref} from "vue";

const users = ref([
  { id : 1, name : '유관순', age : 16},
  { id : 2, name : '홍길동', age : 20},
  { id : 3, name : '선덕여왕', age : 30}
]);
</script>

<template>
<ul>
  <li v-for="user in users" :key="user.id">
    <slot :user="user">
      <!-- 부모가 아무 값도 주지 않았을 경우 기본 콘텐츠 정의 -->
      {{ user.name }} {{ user.age }}
    </slot>
  </li>
</ul>
</template>

<style scoped>

</style>