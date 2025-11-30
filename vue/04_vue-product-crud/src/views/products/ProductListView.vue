<template>
  <section>
    <h2>상품 목록</h2>

    <!-- 검색 폼 컴포넌트 (재사용) -->
    <ProductSearchForm
        :search-form="searchForm"
        @search="onSearch"
        @reset="onReset"
    />

    <el-button type="primary" @click="goCreate" style="margin-bottom: 12px">
      상품 등록
    </el-button>

    <!-- 테이블 컴포넌트 (재사용) -->
    <ProductTable
        :products="products"
        :loading="loading"
        :pagination="pagination"
        :page-size="pageSize"
        :current-page="currentPage"
        @page-change="onPageChange"
        @detail="goDetail"
        @edit="goEdit"
    />

    <p v-if="errorMessage" class="error">
      {{ errorMessage }}
    </p>
  </section>
</template>

<script setup>
// TODO:
// 1) axios 래퍼(fetchProducts) 호출
// 2) 로딩/에러 상태 관리
// 3) 검색/페이지네이션과 router query 연동

import { reactive, ref, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { fetchProducts } from '../../api/productApi';
import ProductSearchForm from '../../components/products/ProductSearchForm.vue';
import ProductTable from '../../components/products/ProductTable.vue';

const router = useRouter();
const route = useRoute();

const loading = ref(false);
const errorMessage = ref('');

const products = ref([]);
const pagination = ref(null);

const searchForm = reactive({
  productName: route.query.productName || '',
  categoryCode: route.query.categoryCode ? Number(route.query.categoryCode) : null
});

const currentPage = ref(route.query.page ? Number(route.query.page) : 1);
const pageSize = 10;

// TODO: 목록 조회 함수 구현
// 1) loading true
// 2) fetchProducts 호출
// 3) products, pagination 세팅
// 4) 에러 처리 후 finally 에서 loading false
const loadList = async () => {

  loading.value = true; // api 통신 시작
  errorMessage.value = '';
  try {
    const result = await fetchProducts({
      page : currentPage.value,
      size : pageSize,
      categoryCode : searchForm.categoryCode,
      productName : searchForm.productName
    });

    products.value = result.products || [];
    pagination.value = result.pagination || null;
  } catch(e) {
    console.log(e);
    errorMessage.value = e.message || '목록 조회 중 오류가 발생했습니다.'
  } finally {
    loading.value = false;  // api 통신 종료
  }

};

// TODO: 검색 버튼 클릭 시 동작
// 1) currentPage = 1
// 2) router.push 로 query 동기화
// 3) loadList 호출
const onSearch = () => {
  currentPage.value = 1;
  router.push({
    name : 'product-list',
    query : {
      page : currentPage.value,
      productName : searchForm.productName || undefined,
      categoryCode : searchForm.categoryCode || undefined
    }
  })
  loadList();
};

// TODO: 초기화 버튼 클릭 시 동작
// 1) searchForm 값 리셋
// 2) onSearch 호출
const onReset = () => {
  searchForm.productName = '';
  searchForm.categoryCode = null;
  onSearch();
};

// TODO: 페이지 변경 시 동작
// 1) currentPage 변경
// 2) router.push 로 query 동기화
// 3) loadList 호출
const onPageChange = (page) => {
  currentPage.value = page;
  router.push({
    name : 'product-list',
    query : {
      page : currentPage.value,
      productName : searchForm.productName || undefined,
      categoryCode : searchForm.categoryCode || undefined
    }
  })
  loadList();
};

// TODO: 상세/수정/등록 이동 함수 구현
const goDetail = (productCode) => {
  router.push({
    name : 'product-detail',
    params : { productCode }
  })
};

const goEdit = (productCode) => {
  router.push({
    name : 'product-edit',
    params : { productCode }
  })
};

const goCreate = () => {
  router.push({
    name : 'product-create'
  })
};

// TODO: 컴포넌트 마운트 시 최초 목록 조회
onMounted(() => {
  loadList()
});
</script>

<style scoped>
.error {
  color: #f56c6c;
  margin-top: 8px;
}
</style>
