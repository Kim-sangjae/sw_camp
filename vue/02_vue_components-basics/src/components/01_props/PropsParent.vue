<script setup lang="ts">

import PropsChild from "@/components/01_props/PropsChild.vue";
import DarkMode from "@/components/01_props/DarkMode.vue";
import {ref} from "vue";
import ReadOnlyProps from "@/components/01_props/ReadOnlyProps.vue";

const darkState = ref(false);
const toggleDarkMode = () => {
  darkState.value = !darkState.value;
}
const readValue = ref('읽기 전용 값');
</script>

<template>
  <div :class="{ container : true , dark : darkState}">

    <h2>01. Props & Emit 예제</h2>
    <div class="block">
      <!--
      부모 컴포넌트에서 자식 컴포넌트로 props 전달
    -->
      <h3>정상 입력 버전</h3>
      <!--
       : 이 붙으면 JavaScript 표현식으로 해석할 수 있다.
       -->
      <PropsChild
          title="정상제목"
          :count="10"
          :isActive="true"
      />
      <!--  <h3>경고 버전</h3>
        <PropsChild
            :title="undefined"
            :count="undefined"
            isActive="true"
        />-->
    </div>

    <div class="block">
      <h2>emit으로 부모 상태 변경</h2>
      현재 다크모드 상태 : {{ darkState ? 'ON' : 'OFF'}}
<!--
DarkMode 컴포넌트가 toggle 이벤트를 emit 하면 부모의 toggleDarkMode 함수가 실행
-->
      <DarkMode @toggle="toggleDarkMode"/>
    </div>

    <div class="block">
      <h2>Props Read Only 테스트</h2>
      부모의 value : <input type="text" v-model="readValue"/>
      <ReadOnlyProps
      :read-value="readValue"
      />
    </div>



  </div>

</template>

<style scoped>
.container {
  padding: 16px;
  border: 1px solid #ddd;
}
.block {
  margin-top: 16px;
  padding: 8px;
  border-top: 1px dashed #ccc;
}
.dark {
  background-color: black;
  color: white;
}
input {
  margin-left: 4px;
}
</style>