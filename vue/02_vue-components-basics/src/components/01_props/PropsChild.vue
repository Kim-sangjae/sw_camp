<script setup>
/* 부모가 넘겨준 props를 자식에서 정의하는 문법 */
 const props = defineProps({
   title : {
     type : String,
     required : true
   },
   count : {
     type : Number,
     default : 0
   },
   isActive : {
     type : Boolean
   }
 });
</script>

<template>
  <div class="box">
    <p>title(필수) : {{ props.title }}</p>
    <p>count(기본 값 존재) : {{ props.count }}</p>
    <p>isActive(Boolean) : {{ props.isActive }}</p>
  </div>
</template>

<style scoped>
.box {
  padding : 8px;
  border : 1px solid #aaa;
}
</style>