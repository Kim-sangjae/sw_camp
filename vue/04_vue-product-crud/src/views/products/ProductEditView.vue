<template>
  <section>
    <h2>상품 수정</h2>

    <ProductForm
        v-if="loaded"
        :initial-form="initialForm"
        :submitting="submitting"
        :error-message="errorMessage"
        submit-text="수정"
        :show-status="true"
        @submit="handleSubmit"
        @cancel="goDetail"
    />

    <el-skeleton v-else animated :rows="4" />

    <p v-if="loadError" class="error">
      {{ loadError }}
    </p>
  </section>
</template>

<script setup>
// 1) 기존 상품 상세 데이터를 불러와서 Form 초기값으로 사용하는 흐름
// 2) updateProduct 호출 후 상세 페이지로 이동

import { reactive, ref, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { ElMessage } from 'element-plus';
import ProductForm from '../../components/products/ProductForm.vue';
import { fetchProductDetail, updateProduct } from '../../api/productApi';

const router = useRouter();
const route = useRoute();

const productCode = Number(route.params.productCode);

const initialForm = reactive({
  productName: '',
  productPrice: null,
  productDescription: '',
  categoryCode: null,
  productStock: null,
  status: 'USABLE',
  productImageUrl: null
});

const loaded = ref(false);
const loadError = ref('');
const submitting = ref(false);
const errorMessage = ref('');

// TODO: 상세 정보 로드 함수 구현
// 1) loaded=false, loadError 초기화
// 2) fetchProductDetail(productCode) 호출
// 3) initialForm 에 값 매핑
// 4) 성공 시 loaded=true, 실패 시 loadError 설정
const loadDetail = async () => {
  loaded.value = false;
  loadError.value = '';

  try {
    const result = await fetchProductDetail(productCode);
    const product = result.product;
    initialForm.productName = product.productName;
    initialForm.productPrice = product.productPrice;
    initialForm.productDescription = product.productDescription;
    initialForm.categoryCode = product.category?.categoryCode;
    initialForm.productStock = product.productStock;
    initialForm.status = 'USABLE';
    initialForm.productImageUrl = product.productImageUrl;
    loaded.value = true;
  } catch (e) {
    console.log(e);
    loadError.value = e.message || '상품 정보를 불러온느 중 오류가 발생했습니다'
  }
};

// TODO: 수정 submit 처리
// 1) submitting=true
// 2) updateProduct(productCode, form) 호출
// 3) 성공 시 메시지 + 상세 페이지 이동
// 4) 실패 시 errorMessage 설정
// 5) finally 에서 submitting=false
const handleSubmit = async (form) => {
  submitting.value = true;
  errorMessage.value = '';

  try {
    await updateProduct(productCode, form);
    ElMessage.success('상품이 수정 되었습니다.')
    router.push({ name : 'product-detail', params : { productCode }})
  } catch (e) {
    console.log(e);
    errorMessage.value = e.message || '상품 수정 중 오류가 발생했습니다'
  } finally {
    submitting.value = false;
  }
};

const goDetail = () => {
  router.push({ name: 'product-detail', params: { productCode } });
};

onMounted(() => {
  loadDetail();
});
</script>

<style scoped>
.error {
  color: #f56c6c;
  margin-top: 8px;
}
</style>
