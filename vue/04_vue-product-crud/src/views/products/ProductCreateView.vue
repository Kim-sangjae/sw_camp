<template>
  <section>
    <h2>상품 등록</h2>

    <ProductForm
        :initial-form="initialForm"
        :submitting="submitting"
        :error-message="errorMessage"
        submit-text="등록"
        :show-status="false"
        @submit="handleSubmit"
        @cancel="goList"
    />
  </section>
</template>

<script setup>
// TODO 포인트:
// - createProduct API 호출
// - 성공 시 상세 페이지로 이동
// - 실패 시 에러 메시지 처리

import { reactive, ref } from 'vue';
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
import ProductForm from '../../components/products/ProductForm.vue';
import { createProduct } from '../../api/productApi';

const router = useRouter();

const initialForm = reactive({
  productName: '',
  productPrice: null,
  productDescription: '',
  categoryCode: null,
  productStock: null,
  productImageUrl: null,
  status: 'USABLE'
});

const submitting = ref(false);
const errorMessage = ref('');

// TODO: 폼 제출 시 동작 구현
// 1) submitting = true
// 2) createProduct(form) 호출
// 3) 성공 시 메시지 + 상세 페이지로 이동
// 4) 실패 시 errorMessage 설정
// 5) finally 에서 submitting false
const handleSubmit = async (form) => {
  submitting.value = true;
  errorMessage.value = '';

  try {
    const result = await createProduct(form);
    ElMessage.success('상품이 등록 되었습니다.')
    router.push({ name : 'product-detail', params :{ productCode : result.productCode }})
  } catch (e) {
    console.log(e);
    errorMessage.value = e.message || '등록 중 오류가 발생하였습니다';
  } finally {
    submitting.value;
  }
};

const goList = () => {
  router.push({ name: 'product-list' });
};
</script>
