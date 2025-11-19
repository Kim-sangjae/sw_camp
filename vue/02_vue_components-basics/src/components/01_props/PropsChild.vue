<script setup >
const props = defineProps({
  title : {
   type : String,
   required : true
  },
  count : {
    Number,
  default : 0
  },
  isActive : {
    type : Boolean
  }
});
</script>

<template>
<div class="box">
  <p>title(필수) : {{props.title}}</p>
  <p>count(기본값 0) : {{props.count}}</p>
  <p>isActive(Boolean) : {{props.isActive}}</p>
</div>
</template>

<style scoped>
.box {
  padding : 8px;
  border : 1px solid #aaa;
}
</style>