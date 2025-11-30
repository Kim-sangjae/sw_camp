<script setup>
import { reactive, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useAuthStore } from '@/stores/authStore';

const route = useRoute();
const router = useRouter();
const authStore = useAuthStore();

const formRef = ref(null);

const form = reactive({
  username: '',
  password: '',
});

const errorMessage = ref(null);

// 간단한 검증 규칙
const rules = {
  username: [
    { required: true, message: '아이디를 입력해 주세요.', trigger: 'blur' },
    { min: 3, message: '아이디는 최소 3자 이상이어야 합니다.', trigger: 'blur' },
  ],
  password: [
    { required: true, message: '비밀번호를 입력해 주세요.', trigger: 'blur' },
    { min: 6, message: '비밀번호는 최소 6자 이상이어야 합니다.', trigger: 'blur' },
  ],
};

const onSubmit = async () => {
  if (!formRef.value) return;

  errorMessage.value = null;

  try {
    await formRef.value.validate();
  } catch {
    return;
  }

  // TODO: authStore.login 호출 및 redirect 처리, 실패 시 errorMessage 설정 로직 작성
  const result = await authStore.login(form);

  if(!result.success) {
    errorMessage.value = result.message;
    return;
  }

  const redirect = route.query.redirect || '/'
  router.push(redirect);

};

const goRegister = () => {
  router.push({name: 'register'});
};
</script>

<template>
  <div class="auth-container">
    <el-card class="auth-card">
      <h2>로그인</h2>

      <el-form
          ref="formRef"
          :model="form"
          :rules="rules"
          label-width="80px"
          @submit.prevent="onSubmit"
      >
        <el-form-item label="아이디" prop="username">
          <el-input v-model="form.username" autocomplete="username"/>
        </el-form-item>

        <el-form-item label="비밀번호" prop="password">
          <el-input
              v-model="form.password"
              type="password"
              autocomplete="current-password"
          />
        </el-form-item>

        <el-form-item>
          <el-button
              type="primary"
              :loading="authStore.loading"
              @click="onSubmit"
          >
            로그인
          </el-button>
          <el-button type="text" @click="goRegister">
            회원가입
          </el-button>
        </el-form-item>
      </el-form>

      <el-alert
          v-if="errorMessage"
          type="error"
          :closable="false"
          :title="errorMessage"
          class="mt-2"
      />
    </el-card>
  </div>
</template>

<style scoped>
.auth-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: calc(100vh - 56px);
}

.auth-card {
  width: 400px;
}

.mt-2 {
  margin-top: 8px;
}
</style>
