// 다양한 사용법
const pants = {
    productName : '배기 팬츠',
    color : '검정색',
    price : 33000
};

// 프로퍼티가 많은 복잡한 객체에서 원하는 정보만 추출하고 싶을 때 사용
const { productName } = pants;
console.log(productName);

// 객체에 존재하지 않는 프로퍼티에 기본 값을 설정해서 사용
const shirts = {
    productName : '베이직 셔츠',
};
const { productName : pn , color : co = "무색", price : pr = 0 } = shirts;
console.log(pn);
console.log(co);
console.log(pr);

// rest parameter를 활용해서 나머지 요소를 한 번에 가져 올 수 있다.
const { productName : pn2, ...rest } = pants;
console.log(pn2);
console.log(rest);

// let 키워드 없이 작성 되었을 경우의 문제
let pn3, co3, pr3;
// 구조 분해 할당 문법이 아니라 코드 블럭 중괄호로 해석해서 발생하는 문제 => 소괄호로 영역 감싸기
// { productName : pn3, color : co3, price : pr3 } = pants;
({ productName : pn3, color : co3, price : pr3 } = pants);
console.log(pn3);
console.log(co3);
console.log(pr3);