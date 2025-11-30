<template>
  <div>
    <el-table
        v-loading="loading"
        :data="products"
        style="width: 100%"
        empty-text="데이터가 없습니다."
    >
      <el-table-column prop="productCode" label="코드" width="80" />
      <el-table-column prop="productName" label="상품명" />
      <el-table-column prop="productPrice" label="가격" width="120">
        <template #default="{ row }">
          {{ row.productPrice?.toLocaleString() }} 원
        </template>
      </el-table-column>
      <el-table-column prop="productStock" label="재고" width="100" />
      <el-table-column label="카테고리" width="160">
        <template #default="{ row }">
          {{ row.category?.categoryName }} ({{ row.category?.categoryCode }})
        </template>
      </el-table-column>
      <el-table-column label="이미지" width="120">
        <template #default="{ row }">
          <img
              v-if="row.productImageUrl"
              :src="row.productImageUrl"
              alt="thumbnail"
              style="max-width: 80px"
          />
        </template>
      </el-table-column>
      <el-table-column label="액션" width="180">
        <template #default="{ row }">
          <el-button size="small" @click="$emit('detail', row.productCode)">
            상세
          </el-button>
          <el-button size="small" type="primary" @click="$emit('edit', row.productCode)">
            수정
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="pagination-wrapper" v-if="pagination">
      <el-pagination
          layout="prev, pager, next"
          :page-size="pageSize"
          :current-page="currentPage"
          :total="pagination.totalItems"
          @current-change="onPageChange"
      />
    </div>
  </div>
</template>

<script setup>
const props = defineProps({
  products: {
    type: Array,
    default: () => []
  },
  loading: {
    type: Boolean,
    default: false
  },
  pagination: {
    type: Object,
    default: null
  },
  pageSize: {
    type: Number,
    default: 10
  },
  currentPage: {
    type: Number,
    default: 1
  }
});

const emits = defineEmits(['page-change', 'detail', 'edit']);

const onPageChange = (page) => {
  emits('page-change', page);
};
</script>

<style scoped>
.pagination-wrapper {
  margin-top: 16px;
  display: flex;
  justify-content: center;
}
</style>
