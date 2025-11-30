<script setup>
</script>

<template>
  <header>
    <div class="wrapper">
      <h1>Vue Router 테스트</h1>
      <hr>
      <h2>Basic Router</h2>
      <!--
        RouterLink
        a 태그처럼 동작하지만 전체 새로고침 없이 URL만 변경해준다.
        to 속성에 route 경로를 적는다.
       -->
      <nav>
        <RouterLink to="/">HOME</RouterLink>
        <RouterLink to="/pathvariable/1">PathVariable</RouterLink>
        <RouterLink to="/querystring?name=홍길동&age=20">QueryString</RouterLink>
      </nav>

      <h2>Nested Router</h2>
      <nav>
        <!--
          /nested는 레이아웃, 그 안쪽 RouterView에 children이 표시되게 한다.
        -->
        <RouterLink to="/nested/home">중첩 HOME VIEW</RouterLink>
        <RouterLink to="/nested/other">중첩 OTHER VIEW</RouterLink>
      </nav>

      <h2>Auth + Guard 예제</h2>
      <nav>
        <RouterLink to="/login">Login</RouterLink>
        <RouterLink to="/mypage">MyPage(인증 필요 화면)</RouterLink>
      </nav>


    </div>
  </header>
  <main>
    <!--
      RouterView
      현재 URL에 매칭 되는 컴포넌트를 이 자리에 렌더링 한다.
      header, nav는 그대로 유지 되고 내용만 교체되는 구조가 된다.
    -->
    <RouterView/>
  </main>

</template>

<style scoped>
header {
  text-align: center;
}

.wrapper {
  padding: 16px;
}

nav {
  width: 100%;
  text-align: center;
  margin-top: 1rem;
  font-size: 20px;
}

a {
  margin: 0 8px;
}

/* linkActiveClass: 'active' 와 연결 */
.active {
  color: red;
  text-decoration: underline;
}

main {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 24px;
}
</style>
