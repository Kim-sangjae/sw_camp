# Element Plus 사용 가이드

Element Plus는 Vue 3 기반의 대표적인 UI 컴포넌트 라이브러리이다.  
디자인 + 기능이 완성된 컴포넌트를 가져다 사용할 수 있어 UI 작업 시간을 크게 절약할 수 있다.

https://element-plus.org/
---

# 1. 설치 및 기본 세팅

프로젝트 생성 후 설치:

```bash
npm install element-plus
````

main.js에 전역 등록:

```js
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

createApp(App)
  .use(router)
  .use(ElementPlus)
  .mount('#app')
```

이제 프로젝트 어디서든 `<el-button>`, `<el-form>` 등을 사용할 수 있다.

---

# 2. 자주 사용하는 Element Plus 컴포넌트

CRUD + 검색 + 테이블 UI에서는 다음 요소들이 가장 많이 사용된다.

---

## 2-1. Form 관련

| 컴포넌트                      | 설명               |
| ------------------------- | ---------------- |
| **el-form**               | 폼 전체 컨테이너        |
| **el-form-item**          | label + input 묶음 |
| **el-input**              | 텍스트 입력           |
| **el-select / el-option** | 드롭다운 선택 UI       |
| **el-input-number**       | 숫자 입력            |
| **el-upload**             | 파일 업로드 UI        |

---

## 2-2. 버튼 / 레이아웃

| 컴포넌트                | 설명          |
| ------------------- | ----------- |
| **el-button**       | 버튼          |
| **el-row / el-col** | 반응형 레이아웃    |
| **el-card**         | 카드 스타일 컨테이너 |

---

## 2-3. 데이터 출력 (목록)

| 컴포넌트                | 설명     |
| ------------------- | ------ |
| **el-table**        | 테이블 UI |
| **el-table-column** | 테이블 컬럼 |
| **el-pagination**   | 페이지네이션 |

---

## 2-4. 피드백/상태 UI

| 컴포넌트           | 설명          |
| -------------- | ----------- |
| **el-dialog**  | 모달          |
| **el-message** | 성공/실패 등 메시지 |
| **el-tooltip** | 툴팁          |
| **el-loading** | 로딩 스피너      |

---

# 3. 가장 많이 사용하는 패턴 예시

---

## ✔ 3-1. 입력 필드 (v-model 기반)

```vue
<el-input v-model="form.name" placeholder="상품명 입력" />
```

---

## ✔ 3-2. Select 박스

```vue
<el-select v-model="form.categoryCode">
  <el-option label="식사" :value="1" />
  <el-option label="디저트" :value="2" />
  <el-option label="음료" :value="3" />
</el-select>
```

---

## ✔ 3-3. Form + Validation

```vue
<el-form :model="form" :rules="rules" ref="formRef">
  <el-form-item label="상품명" prop="name">
    <el-input v-model="form.name" />
  </el-form-item>
</el-form>
```

---

## ✔ 3-4. 테이블 + 페이지네이션

```vue
<el-table :data="products">
  <el-table-column prop="productName" label="상품명" />
  <el-table-column prop="productPrice" label="가격" />
</el-table>

<el-pagination
  :page-size="10"
  :total="pagination.totalItems"
  @current-change="onPageChange"
/>
```

---

# 4. Element Plus 사용 기준

* **폼 화면(form)** → `el-form` + `el-form-item` + `el-input` + `el-select`
* **목록 화면(list)** → `el-table` + `el-table-column` + `el-pagination`
* **CRUD 공통 버튼** → `el-button`
* **파일 업로드** → `input type="file"` 또는 `el-upload`
* **상세 화면(detail)** → `el-card` 또는 직접 스타일링
* **로딩 표시** → `v-loading` 또는 Loading 서비스

즉, “직접 HTML/CSS로 제작하기보다 Element Plus 컴포넌트를 우선 사용”하는 것이 기본적인 패턴이다.

---

# 5. 이 프로젝트에서 Element Plus가 사용되는 위치

| 파일                        | 역할         | Element Plus 사용                          |
| ------------------------- | ---------- | ---------------------------------------- |
| **ProductForm.vue**       | 상품 입력/수정 폼 | el-form, el-input, el-select, el-button  |
| **ProductSearchForm.vue** | 검색 조건 입력   | el-form, el-input, el-select, el-button  |
| **ProductTable.vue**      | 목록 테이블     | el-table, el-table-column, el-pagination |
| **ProductDetailView.vue** | 상세 정보 페이지  | el-card, el-row/col                      |
| **AppHeader.vue**         | 상단 메뉴      | el-menu                                  |

---

# 6. Element Plus를 왜 배우는가?

* 디자인 신경 쓰지 않고 백엔드 로직·API 연동에 집중할 수 있다.
* form validation, table, pagination 같은 공통 기능을 빠르게 구성 가능하다.
* 컴포넌트 구조와 분리 기준을 배우기에 적합하다 (SearchForm, Table, Form 재사용).

---
