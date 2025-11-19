<!--
반응형 변수 , 반응형 변수 변경 함수 , 라이프 사이클 훅 등 setup 안에 정의할 수 있는
내용을 작성하는 공간 ViewModel의 개념을 작성하는 영역
-->
<script setup>

import MyComponent from "@/components/00_components/MyComponent.vue";
import PropsParent from "@/components/01_props/PropsParent.vue";
import SlotParent from "@/components/02_slot/SlotParent.vue";
import ScopedSlotParent from "@/components/03_scoped-slot/ScopedSlotParent.vue";
import ProvideParent from "@/components/04_provide-inject/ProvideParent.vue";
</script>


<!--
사용자 화면에 보여지는 부분을 내보낼 때 작성하는 공간
다른 엘리먼트 감싸주는 역할을 수행 , 화면에 렌더링 될 때는 제거됨 (<template> 라는 태그가 제거)
-->
<template>
<header>
  <h1>Vue Component Basics</h1>
  <h3>단일 컴포넌트 기본 구조</h3>
  <MyComponent/>
</header>
  <main>
    <h1>01.Props & Emit 기본</h1>
    <PropsParent/>
    <h1>02.Slot & NamedSlot & Dynamic Slot</h1>
    <SlotParent/>
    <h1>03.Scoped Slot</h1>
    <ScopedSlotParent/>
    <h1>04. provide & inject</h1>
    <ProvideParent/>
  </main>
</template>

<!--
현재 컴포넌트에 적용 되는 스타일을 작성
각 컴포넌트 별 data-v-고유값 에 적용된다

공통적인 css는 src/asserts/css 디렉토리 하위에 파일로 분리하고
main.js에서 import 해서 전역적으로 사용하는 것이 일반적이다
-->
<style scoped>
header {
  padding: 16px;
  border-bottom: 1px solid #ddd;
}

main {
  padding: 16px;
}

/* 하위 컴포넌트에서 사용하는 .container 공통 스타일 */
:deep(.container) {
  display: flex;
  flex-direction: column;
  align-items: center;
  border: 1px solid lightgray;
  margin: 30px 0;
  padding: 30px;
}
</style>
