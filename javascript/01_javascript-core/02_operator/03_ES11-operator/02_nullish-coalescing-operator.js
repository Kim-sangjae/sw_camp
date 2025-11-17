// null 병합 연산자
// 좌항의 피연산자가 null or undefined 일때 우항의 피연산자를 반환
// 그렇지 않으면 좌항의 피연산자를 반환 (변수 기본값 설정 용도)
var obj = null;
var test = obj ?? '기본값';
console.log(test);

// 단축 평가 활용 시 빈 문자열과 같은 falsy 값을 false로 취급하는 문제가 있었다.
var val = '' || '기본값';
var val2 = '' ?? '기본값';
console.log(val);
console.log(val2);