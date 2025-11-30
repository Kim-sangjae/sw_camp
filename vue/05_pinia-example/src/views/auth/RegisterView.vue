<script setup>
import { reactive, ref } from 'vue';
import { useRouter } from 'vue-router';
import { registerApi } from '@/api/authApi';
import { ElMessage } from 'element-plus';

const router = useRouter();

const formRef = ref(null);

const form = reactive({
  username: '',
  password: '',
});

// 간단한 검증 규칙
// - username: 필수, 최소 3자
// - password: 필수, 최소 6자
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

const loading = ref(false);

const onSubmit = async () => {
  if (!formRef.value) return;

  // 유효성 검사 실패 시 회원가입 요청 자체를 하지 않음
  try {
    await formRef.value.validate();
  } catch {
    return;
  }

  loading.value = true;

  try {
    // TODO: registerApi 호출 및 성공/실패에 따른 ElMessage, 라우팅 처리 로직 작성
    const res = await registerApi(form);
    const { success, message } = res.data;

    if(success) {
      ElMessage.success('회원 가입이 완료 되었습니다. 로그인 해주세요.')
      router.push({ name : 'login' })
    } else {
      ElMessage.error(message || '회원가입에 실패했습니다.')
    }
  } catch (e) {
    ElMessage.error(e.response?.data?.message || '회원 가입 요청 중 오류 발생')
  } finally {
    loading.value = false;
  }
};

const goLogin = () => {
  router.push({ name: 'login' });
};
</script>

<template>
  <div class="auth-container">
    <el-card class="auth-card">
      <h2>회원가입</h2>

      <el-form
          ref="formRef"
          :model="form"
          :rules="rules"
          label-width="80px"
          @submit.prevent="onSubmit"
      >
        <el-form-item label="아이디" prop="username">
          <el-input v-model="form.username" />
        </el-form-item>

        <el-form-item label="비밀번호" prop="password">
          <el-input v-model="form.password" type="password" />
        </el-form-item>

        <el-form-item>
          <el-button type="primary" :loading="loading" @click="onSubmit">
            가입하기
          </el-button>
          <el-button @click="goLogin">로그인</el-button>
        </el-form-item>
      </el-form>
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
</style>