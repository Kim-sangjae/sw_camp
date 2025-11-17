// 스프레드 문법의 활용 => 배열 또는 객체 복사
const arr = [10, 20, 30];
const obj = { name : '홍길동', age : 18 };

const arrCopy = [...arr];
const objCopy = { ...obj };
console.log(arrCopy);
console.log(arr === arrCopy);
console.log(objCopy);
console.log(obj === objCopy);