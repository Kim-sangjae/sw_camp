<script setup >

import SlotChild from "@/components/02_slot/SlotChild.vue";
import {reactive, ref} from "vue";
import NamedSlotChild from "@/components/02_slot/NamedSlotChild.vue";
import DynamicSlotChild from "@/components/02_slot/DynamicSlotChild.vue";

const slotData = ref("hello slot");
const dynamicName = ref("north");

const obj = reactive({
  name : 'Vue',
  founder : 'Even Yu',
  img : 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR6bAc0cdyuLg0WtInF14xQifCI5P88MiC0kULnoQC61SMiyZzZPp2jNm-Dnq8orAgmcuwcaCggsZAjw9t13Q5mAi_95zKnb9nbL0NOzw&s=10'
});

</script>

<template>
<div class = 'container'>
  <h2>02. Slot 예제</h2>

  <div class="block">
    <h3>기본 slot 테스트</h3>
<!--
같은 Child 컴포넌트를 쓰되 , 안에 들어가는 컨텐츠를 화면마다 다르게 사용하고 싶을 때
-->
    <input type="text" v-model="slotData"/>
    <SlotChild>
      <h3>{{slotData}}</h3>
    </SlotChild>
  </div>

  <div class="block">
    <h3>NamedSlot 테스트</h3>
    <NamedSlotChild>
<!-- title 같은 영역에 대응 -->
      <template v-slot:name>
        <h1>name: {{obj.name}}</h1>
      </template>
      <!-- 설명 같은 영역에 대응-->
      <template v-slot:founder>
        <p>founder: {{obj.founder}}</p>
      </template>
      <!-- 이미지 영역에 대응 -->
      <template v-slot:img>
        <img :src="obj.img" :alt="obj.name"/>
      </template>
    </NamedSlotChild>
  </div>

  <div class="block">
    <h3>DynamicSlot 테스트</h3>
    <input type="text" v-model="dynamicName"/>
<!--
DynamicSlotChild는 4분할 레이아웃 틀만 책임지고 어디에 어떤 내용을 넣을지는 부모가 런타임에 선택
-->
    <DynamicSlotChild>
      <template #[dynamicName]>
        {{dynamicName}} : 입력 된 값이 slot의 name과 일치할 때 나타남!
      </template>
    </DynamicSlotChild>
  </div>


</div>
</template>

<style scoped>
img{
  width : 200px;
  height : 200px;
}
</style>