<template>
  <el-form
      ref="formRef"
      :model="localForm"
      :rules="rules"
      label-width="120px"
      @submit.prevent="onSubmit"
  >
    <el-form-item label="상품명" prop="productName">
      <el-input v-model="localForm.productName" />
    </el-form-item>

    <el-form-item label="가격" prop="productPrice">
      <el-input
          v-model.number="localForm.productPrice"
          type="number"
          min="0"
      />
    </el-form-item>

    <el-form-item label="설명" prop="productDescription">
      <el-input
          v-model="localForm.productDescription"
          type="textarea"
          :rows="3"
      />
    </el-form-item>

    <el-form-item label="카테고리" prop="categoryCode">
      <el-select
          v-model="localForm.categoryCode"
          placeholder="카테고리 선택"
          style="width: 200px"
      >
        <el-option label="식사" :value="1" />
        <el-option label="디저트" :value="2" />
        <el-option label="음료" :value="3" />
      </el-select>
    </el-form-item>

    <el-form-item label="재고 수량" prop="productStock">
      <el-input
          v-model.number="localForm.productStock"
          type="number"
          min="0"
      />
    </el-form-item>

    <el-form-item label="상태" v-if="showStatus" prop="status">
      <el-select v-model="localForm.status" placeholder="상태 선택" style="width: 200px">
        <el-option label="사용 가능" value="USABLE" />
        <el-option label="주문 불가" value="DISABLE" />
        <el-option label="삭제" value="DELETED" />
      </el-select>
    </el-form-item>

    <el-form-item label="상품 이미지">
      <input type="file" @change="onFileChange" />
      <div v-if="imagePreviewUrl" class="preview">
        <img :src="imagePreviewUrl" alt="미리보기" />
      </div>
    </el-form-item>

    <el-form-item>
      <el-button type="primary" :loading="submitting" native-type="submit">
        {{ submitText }}
      </el-button>
      <el-button @click="onCancel">
        취소
      </el-button>
    </el-form-item>

    <p v-if="errorMessage" class="error">
      {{ errorMessage }}
    </p>
  </el-form>
</template>

<script setup>

import { reactive, watch, computed, ref } from 'vue';

const props = defineProps({
  initialForm: {
    type: Object,
    required: true
  },
  submitting: {
    type: Boolean,
    default: false
  },
  errorMessage: {
    type: String,
    default: ''
  },
  submitText: {
    type: String,
    default: '저장'
  },
  showStatus: {
    type: Boolean,
    default: false
  }
});

const emits = defineEmits(['submit', 'cancel']);

const formRef = ref(null);

const localForm = reactive({
  productName: '',
  productPrice: null,
  productDescription: '',
  categoryCode: null,
  productStock: null,
  status: 'USABLE',
  productImgFile: null,
  productImageUrl: null
});

// TODO: validation rules 작성
// - productName: 필수, 길이 제한
// - productPrice: 필수, 0 이상 숫자
// - productDescription: 필수, 길이 제한
// - categoryCode: 필수
// - productStock: 필수, 0 이상 숫자
// - status: 필수 (showStatus=true 일 때)
const rules = {
  productName: [
    { required: true, message: '상품명을 입력하세요.', trigger: 'blur' },
    { min : 1, max : 10, message: '상품명은 1~10자 내외여야 합니다.', trigger: 'blur'}
  ],
  productPrice: [
    { required: true, message: '가격을 입력하세요.', trigger: 'blur' },
    { type : 'number', min : 1, message: '가격은 0 이상이어야 합니다.', trigger: 'blur'}
  ],
  productDescription: [
    { required: true, message: '상품설명을 입력하세요.', trigger: 'blur' },
    { min : 10, max : 100, message: '상품명은 10~100자 내외여야 합니다.', trigger: 'blur'}
  ],
  categoryCode: [
    { required: true, message: '카테고리를 선택하세요', trigger: 'change' },
  ],
  productStock: [
    { required: true, message: '재고를 입력하세요.', trigger: 'blur' },
    { type : 'number', min : 0, message: '재고는 0 이상이어야 합니다.', trigger: 'blur'}
  ],
  status: [
    { required: true, message: '상품상태를 선택하세요', trigger: 'change' },
  ],
};

// initialForm 이 변경될 때 localForm 으로 복사
watch(
    () => props.initialForm,
    (value) => {
      if (!value) return;
      localForm.productName = value.productName ?? '';
      localForm.productPrice = value.productPrice ?? null;
      localForm.productDescription = value.productDescription ?? '';
      localForm.categoryCode = value.categoryCode ?? null;
      localForm.productStock = value.productStock ?? null;
      localForm.status = value.status ?? 'USABLE';
      localForm.productImgFile = null;
      localForm.productImageUrl = value.productImageUrl ?? null;
    },
    { immediate: true, deep: true }
);

const imagePreviewUrl = computed(() => {
  if (localForm.productImgFile) {
    return URL.createObjectURL(localForm.productImgFile);
  }
  return localForm.productImageUrl || null;
});

const onFileChange = (event) => {
  const file = event.target.files && event.target.files[0];
  if (file) {
    localForm.productImgFile = file;
  }
};

// TODO: submit 시 validation 후 상위로 데이터 emit
const onSubmit = () => {

  if(!formRef.value) {
    emits('submit', {...localForm})
  }

  formRef.value.validate((valid) => {
    if(valid) {
      emits('submit', {...localForm})
    }
  })
};

const onCancel = () => {
  emits('cancel');
};
</script>

<style scoped>
.preview img {
  max-width: 200px;
  margin-top: 8px;
}

.error {
  color: #f56c6c;
}
</style>
