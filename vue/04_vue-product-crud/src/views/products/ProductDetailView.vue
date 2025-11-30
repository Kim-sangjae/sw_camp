<template>
  <section>
    <el-button @click="goList" style="margin-bottom: 12px">
      목록으로
    </el-button>

    <el-card v-loading="loading" class="detail-card">
      <template #header>
        <div class="card-header">
          <span>상품 상세</span>
          <div class="actions">
            <el-button size="small" type="primary" @click="goEdit">
              수정
            </el-button>
            <el-button size="small" type="danger" @click="onDelete">
              삭제
            </el-button>
          </div>
        </div>
      </template>

      <div v-if="product" class="detail-layout">
        <div class="info-column">
          <h2 class="product-title">
            {{ product.productName }}
          </h2>

          <p class="category">
            <el-tag type="info" size="small">
              {{ product.category?.categoryName || '카테고리 미지정' }}
            </el-tag>
            <span v-if="product.category?.categoryCode" class="category-code">
              ({{ product.category.categoryCode }})
            </span>
          </p>

          <div class="meta-grid">
            <div class="meta-item">
              <span class="meta-label">상품 코드</span>
              <span class="meta-value">{{ product.productCode }}</span>
            </div>
            <div class="meta-item">
              <span class="meta-label">가격</span>
              <span class="meta-value">
                {{ product.productPrice?.toLocaleString() }} 원
              </span>
            </div>
            <div class="meta-item">
              <span class="meta-label">재고</span>
              <span class="meta-value">
                {{ product.productStock?.toLocaleString() }} 개
              </span>
            </div>
          </div>

          <div class="description-block">
            <h3 class="description-title">상품 설명</h3>
            <p class="description-text">
              {{ product.productDescription || '등록된 상품 설명이 없습니다.' }}
            </p>
          </div>
        </div>

        <div class="image-column" v-if="product.productImageUrl">
          <div class="image-wrapper">
            <img :src="product.productImageUrl" alt="상품 이미지" />
          </div>
          <p class="image-caption">
            실제 서비스에서는 리스트 썸네일과 상세 이미지를 구분해서 사용할 수 있다.
          </p>
        </div>
      </div>

      <p v-else-if="!loading">상품 정보를 찾을 수 없습니다.</p>
    </el-card>

    <p v-if="errorMessage" class="error">
      {{ errorMessage }}
    </p>
  </section>
</template>

<script setup>
// - fetchProductDetail 로 상세 데이터 불러오기
// - deleteProduct 로 삭제 후 목록으로 이동

import { ref, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { ElMessageBox, ElMessage } from 'element-plus';
import { fetchProductDetail, deleteProduct } from '../../api/productApi';

const router = useRouter();
const route = useRoute();

const product = ref(null);
const loading = ref(false);
const errorMessage = ref('');

const productCode = Number(route.params.productCode);

// TODO: 상세 조회 함수 구현
// 1) loading=true
// 2) fetchProductDetail(productCode) 호출
// 3) product.value 설정
// 4) 에러 처리
const loadDetail = async () => {
  loading.value = true; // api 호출 시작
  errorMessage.value = '';
  try {
    const result = await fetchProductDetail(productCode);
    product.value = result.product;
  } catch(e) {
    console.error(e);
    errorMessage.value = e.message || '상세 조회 중 오류가 발생했습니다'
  } finally {
    loading.value = false;  // api 호출 종료
  }
};

// 목록, 수정, 삭제 동작
const goList = () => {
  router.push({ name: 'product-list' });
};

const goEdit = () => {
  router.push({ name: 'product-edit', params: { productCode } });
};

// TODO: 삭제 동작 구현
// 1) confirm 모달 띄우기
// 2) deleteProduct(productCode) 호출
// 3) 성공 시 메시지 + 목록 이동
// 4) 실패 시 에러 메시지
const onDelete = () => {
  ElMessageBox.confirm('정말로 이 상품을 삭제하시겠습니까?', '삭제 확인', {
    confirmButtonText : '삭제',
    cancelButtonText : '취소',
    type : 'warning'
  })
      .then(async () => {
        // 삭제 버튼을 눌렀을 때의 동작
        try {
          await deleteProduct(productCode);
          ElMessage.success('삭제되었습니다.')
          await router.replace({name: 'product-list'})
        } catch(e) {
          console.log(e);
          ElMessage.error(e.message || '삭제 중 오류가 발생했습니다.')
        }
      })
      .catch(() => {
        // 취소 버튼을 눌렀을 때의 동작
      })
};

onMounted(() => {
  loadDetail();
});
</script>

<style scoped>
.detail-card {
  max-width: 1100px;
  margin: 0 auto;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.actions {
  display: flex;
  gap: 8px;
}

.detail-layout {
  display: flex;
  gap: 32px;
  align-items: flex-start;
}

.info-column {
  flex: 3;
}

.image-column {
  flex: 2;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

.product-title {
  margin: 0 0 8px;
  font-size: 24px;
  font-weight: 600;
}

.category {
  margin: 0 0 16px;
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 13px;
  color: #666;
}

.category-code {
  color: #999;
}

.meta-grid {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 8px 24px;
  margin-bottom: 24px;
}

.meta-item {
  display: flex;
  flex-direction: column;
}

.meta-label {
  font-size: 12px;
  color: #888;
}

.meta-value {
  font-size: 14px;
  font-weight: 500;
}

.description-block {
  padding: 16px;
  border-radius: 8px;
  background-color: #fafafa;
  border: 1px solid #f0f0f0;
}

.description-title {
  margin: 0 0 8px;
  font-size: 15px;
  font-weight: 600;
}

.description-text {
  margin: 0;
  line-height: 1.6;
  white-space: pre-wrap;
}

.image-wrapper {
  width: 100%;
  border-radius: 8px;
  overflow: hidden;
  border: 1px solid #eee;
  background-color: #fafafa;
}

.image-wrapper img {
  display: block;
  width: 100%;
  max-height: 360px;
  object-fit: cover;
}

.image-caption {
  margin-top: 8px;
  font-size: 12px;
  color: #999;
}

.error {
  color: #f56c6c;
  margin-top: 8px;
}
</style>
