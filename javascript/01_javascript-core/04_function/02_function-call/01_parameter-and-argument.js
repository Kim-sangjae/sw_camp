// 매개변수와 인수
function hello(name) {
    // 함수 호출 시 전달 된 인수를 참조할 수 있다 (인덱스 순서대로 참조)
    console.log('arguments', arguments);
    return `${name}님 안녕하세요~`;
}

// 매개변수는 함수 몸체 외부에서 사용 불가
// console.log(name);

// 매개변수의 개수와 인수의 개수가 일치하는지 체크하지 않는다.
var result = hello();   // 인수가 부족하면 undefined
console.log(result);
result = hello('유관순', '홍길동');   // 인수가 초과되면 무시
console.log(result);

// 인수를 전달하지 않거나 undefined가 전달 될 경우 기본 값이 동작하도록 설정할 수 있다.
function hi(name = '아무개') {
    return `${name}님 안녕!`;
}

console.log(hi('강감찬'));
console.log(hi());












