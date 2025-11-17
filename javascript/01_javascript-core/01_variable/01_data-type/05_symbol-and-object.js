// symbol : ES6에서 추가된 7번째 타입으로 다른 값과 중복 되지 않는 유일무이한 값
// 이름 충돌 위험이 없는 객체의 유일한 프로퍼티 키 값을 만들기 위해 사용
var key = Symbol('key');
console.log(key);
console.log(typeof key);

// object
// 자바스크립트의 데이터 타입은 원시 타입 vs 객체 타입 으로 분류
// 객체 (배열, 함수, ...) 에 대해서는 추후 각자 챕터에서 학습
var obj = {};
obj[key] = 'value';
console.log(obj);