<script setup>
const props = defineProps({
  readValue : {
    type : String,
    default : ''
  }
})
</script>

<template>
  <h3>자식이 전달 받은 readValue : {{ props.readValue }}</h3>
  <!--
    의도적으로 잘못 된 사용 예시
  -->
  자식의 readValue 입력 : <input type="text" v-model="props.readValue"/>
</template>

<style scoped>

</style>