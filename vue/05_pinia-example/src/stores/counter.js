import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const useCounterStore = defineStore('counter', () => {
  /* 1. state
  * ref 함수를 이용해 store에서 관리할 상태 값 정의
  * */
    const count = ref(0)
    /* 2. getters
    * computed로 만드는 state의 파생 값
    * */
  const doubleCount = computed(() => count.value * 2)
    /* 3. actions
    * state의 값을 변경하는 동작을 취하는 함수
    * */
  function increment() {
    count.value++
  }

  /* store를 통해서 접근할 값은 return 객체에 담는다.
  * 노출하지 않을 값이 있다면 빼고 작성한다.
  * */
  return { count, doubleCount, increment }
})
