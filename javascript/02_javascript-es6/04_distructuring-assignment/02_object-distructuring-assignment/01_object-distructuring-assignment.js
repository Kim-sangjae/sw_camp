// 객체 구조 분해 할당
const pants = {
    productName : '배기 팬츠',
    color : '검정색',
    price : 33000
};

const { productName, color, price } = pants;
console.log(productName);
console.log(color);
console.log(price);

// 각 변수의 서술 순서는 무관하며 { 객체 프로퍼티 : 목표 변수 } 의 형태로 작성하여
// 프로퍼티 키 값과 다른 변수명을 설정할 수도 있다.
const { price : pr, color : co, productName : pn } = pants;
console.log(pr);
console.log(co);
console.log(pn);