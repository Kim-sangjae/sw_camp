<script setup>
import { ref, onMounted } from 'vue';
import { fetchMyInfoApi } from '@/api/userApi';

const user = ref(null);
const loading = ref(false);
const errorMessage = ref(null);

const loadMyInfo = async () => {
  loading.value = true;
  errorMessage.value = null;

  try {
    user.value = await fetchMyInfoApi(); // 이미 userDTO만 반환됨
  } catch (e) {
    errorMessage.value =
        e.message || '사용자 정보를 불러오는 중 오류가 발생했습니다.';
  } finally {
    loading.value = false;
  }
};

onMounted(() => {
  loadMyInfo();
});
</script>

<template>
  <div>
    <h2>내 정보</h2>

    <el-skeleton v-if="loading" :rows="3" animated />

    <template v-else>
      <el-alert
          v-if="errorMessage"
          type="error"
          :closable="false"
          :title="errorMessage"
          class="mb-2"
      />

      <el-card v-if="user">
        <el-descriptions :column="1" border>
          <el-descriptions-item label="아이디">
            {{ user.username }}
          </el-descriptions-item>
          <el-descriptions-item label="권한">
            {{ user.role }}
          </el-descriptions-item>
        </el-descriptions>
      </el-card>

      <el-empty
          v-else
          description="사용자 정보를 불러올 수 없습니다."
      />
    </template>
  </div>
</template>

<style scoped>
.mb-2 {
  margin-bottom: 8px;
}
</style>
