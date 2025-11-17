// 옵셔널 체이닝 연산자
// 좌항의 피연산자가 null or undefined이면 undefined를 반환하고
// 그렇지 않으면 우항의 프로퍼티 참조를 이어간다.
var obj = null;
var val = obj?.value;
console.log(val);

// 단축 평가를 이용하면 빈 문자열과 같은 falsy 값을 false로 취급하는 문제가 있었다.
var str = '';
var len = str && str.length;
var len2 = str?.length;
console.log(len);
console.log(len2);