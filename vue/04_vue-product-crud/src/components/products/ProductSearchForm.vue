<template>
  <el-form
      :inline="true"
      :model="searchForm"
      class="search-form"
      @submit.prevent="onSearchClick"
  >
    <el-form-item label="상품명">
      <el-input v-model="searchForm.productName" placeholder="상품명 검색" />
    </el-form-item>

    <el-form-item label="카테고리">
      <el-select
          v-model="searchForm.categoryCode"
          placeholder="전체"
          clearable
          style="width: 160px"
      >
        <el-option label="식사" :value="1" />
        <el-option label="디저트" :value="2" />
        <el-option label="음료" :value="3" />
      </el-select>
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="onSearchClick">
        검색
      </el-button>
      <el-button @click="onResetClick">
        초기화
      </el-button>
    </el-form-item>
  </el-form>
</template>

<script setup>
const props = defineProps({
  // 부모에서 reactive 객체를 넘겨줌
  searchForm: {
    type: Object,
    required: true
  }
});

const emits = defineEmits(['search', 'reset']);

const onSearchClick = () => {
  emits('search');
};

const onResetClick = () => {
  emits('reset');
};
</script>

<style scoped>
.search-form {
  margin-bottom: 16px;
}
</style>
