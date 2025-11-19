<script setup>

</script>

<template>
<!--
제목 영역 , 이미지 영역 처럼 자식이 각각 여러개의 위치를 미리 정해두고
부모가 각 영역에 다른 콘텐츠를 채워 넣을 수 있게 한다.
-->
<slot name="name"></slot>
<slot name="founder"></slot>
<slot name="img"></slot>
</template>

<style scoped>

</style>