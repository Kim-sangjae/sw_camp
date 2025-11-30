<script setup>
import { ref, onMounted } from 'vue';
import { fetchAllUsersApi } from '@/api/userApi';

const users = ref([]);
const loading = ref(false);
const errorMessage = ref(null);

const loadUsers = async () => {
  loading.value = true;
  errorMessage.value = null;

  try {
    users.value = await fetchAllUsersApi(); // 이미 가공된 users 배열만 옴
  } catch (e) {
    errorMessage.value =
        e.message || '회원 목록을 불러오는 중 오류가 발생했습니다.';
  } finally {
    loading.value = false;
  }
};

onMounted(() => {
  loadUsers();
});
</script>

<template>
  <div>
    <h2>모든 회원 정보</h2>

    <el-alert
        v-if="errorMessage"
        type="error"
        :closable="false"
        :title="errorMessage"
        class="mb-2"
    />

    <el-table
        v-loading="loading"
        :data="users"
        style="width: 100%; margin-top: 16px;"
        empty-text="회원 정보가 없습니다."
    >
      <el-table-column prop="username" label="아이디"/>
      <el-table-column prop="role" label="권한"/>
    </el-table>
  </div>
</template>

<style scoped>
.mb-2 {
  margin-bottom: 8px;
}
</style>